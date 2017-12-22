package com.mdvns.mdvn.story.service.impl;

import com.mdvns.mdvn.common.bean.MemberRequest;
import com.mdvns.mdvn.common.bean.model.RoleMember;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestTemplateUtil;
import com.mdvns.mdvn.story.config.WebConfig;
import com.mdvns.mdvn.story.domain.entity.StoryMember;
import com.mdvns.mdvn.story.repository.MemberRepository;
import com.mdvns.mdvn.story.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);


    @Resource
    private MemberRepository memberRepository;

    @Resource
    private WebConfig webConfig;


    @Override
    public Integer buildMembers(Long creatorId, Long storyId, List<MemberRequest> members) {

        //遍历members构建并保存RequirementMember
        Integer size = 0;
        for (MemberRequest memberRequest : members) {
            //遍历保存每个模板角色下的member
            size += memberRequest.getAddList().size();
            saveRoleMembers(creatorId, storyId, memberRequest.getRoleId(), memberRequest.getAddList());
        }
        LOG.info("保存成员映射信息完成");
        return size;
    }

    /**
     * 获取指定requirement的成员信息
     *
     * @param staffId       staffId
     * @param storyId storyId
     * @return list
     */
    @Override
    public List<RoleMember> getRoleMembers(Long staffId, Long storyId, Integer isDeleted) throws BusinessException {
        LOG.info("获取id为【{}】的Story的成员开始...", storyId);
        //查询指定requirement的所有角色id
        List<Long> roles = this.memberRepository.findRoleIdByStoryIdAndIsDeleted(storyId, isDeleted);
        //获取角色id和name
        List<TerseInfo> tmplRoles = getRoles(staffId, roles);

        //如果角色信息为空, 抛出异常
        if (tmplRoles.isEmpty()) {
            LOG.error("角色信息不存在...");
            throw new BusinessException(ErrorEnum.TEMPLATE_ROLE_NOT_EXISTS, "模板角色不存在...");
        }
        //角色成员集合
        List<RoleMember> roleMembers = new ArrayList<>();
        //查询每个角色对应的成员
        for (TerseInfo tmplRole : tmplRoles) {
            RoleMember roleMember = new RoleMember();
            //根据roleId和requirementId查询角色对应的成员
            //如果成员信息为空, 抛出异常
            List<TerseInfo> members = getMembers(staffId, storyId, tmplRole.getId(), isDeleted);
            if (null == members || members.isEmpty()) {
                LOG.error("角色信息不存在...");
                throw new BusinessException(ErrorEnum.STAFF_NOT_EXISTS, "模板角色不存在...");
            }
            //设置templateRole
            roleMember.setTmplRole(tmplRole);
            //设置members
            roleMember.setMembers(members);
            //添加roleMember 到集合中
            roleMembers.add(roleMember);
        }
        LOG.info("获取id为【{}】的Story的成员成功. 成员共【{}】个.", storyId, roleMembers.size());
        return roleMembers;
    }

    /**
     * 修改角色成员
     *
     * @param staffId staffId
     * @param storyId storyId
     * @param roleMembers roleMembers
     */
    @Override
    public void updateRoleMembers(Long staffId, Long storyId, List<MemberRequest> roleMembers) {
        //遍历 roleMembers, 处理每一个角色对应的成员修改
        for (MemberRequest memberRequest : roleMembers) {
            //删除成员
            List<Long> removeList = memberRequest.getRemoveList();
            if (!(null == removeList || removeList.isEmpty())) {
                this.memberRepository.updateIsDeleted(storyId, memberRequest.getRoleId(), memberRequest.getRemoveList(), MdvnConstant.ONE);
            }
            //新增成员
            List<Long> addList = memberRequest.getAddList();
            if (!(null == addList || addList.isEmpty())) {
                updateMembers(staffId, storyId, memberRequest.getRoleId(), memberRequest.getAddList());
            }

        }
    }

    /**
     * 根据roleId查询角色id和name
     *
     * @param staffId staffId
     * @param roles   roles
     * @return list
     */
    private List<TerseInfo> getRoles(Long staffId, List<Long> roles) throws BusinessException {
        LOG.info("根据id集合【{}】获取角色的id和name开始...", roles.toString());
        //查询role的url
        String retrieveRoleUrl = webConfig.getRetrieveRoleUrl();
        //调用Template模块查询role
        return RestTemplateUtil.retrieveBasicInfo(staffId, roles, retrieveRoleUrl);
    }

    /**
     * 查询指定storyId 和 roleId的成员信息
     *
     * @param staffId       staffId
     * @param storyId storyId
     * @param roleId        roleId
     * @return list
     * @throws BusinessException exception
     */
    private List<TerseInfo> getMembers(Long staffId, Long storyId, Long roleId, Integer isDeleted) throws BusinessException {
        LOG.info("获取id为【{}】的Story, roleId为【{}】的角色成员开始...", storyId, roleId);
        //获取成员id和name
        List<Long> ids = this.memberRepository.findMemberIdByStoryIdAndRoleIdAndIsDeleted(storyId, roleId, isDeleted);
        //获取member的url
        String retrieveMembersUrl = webConfig.getRetrieveMembersUrl();
        //调用staff模块获取成员信息
        List<TerseInfo> members = RestTemplateUtil.retrieveBasicInfo(staffId, ids, retrieveMembersUrl);
        MdvnCommonUtil.emptyList(members, ErrorEnum.STAFF_NOT_EXISTS, "Id为【" + ids.toString() + "】的用户不存在.");
        LOG.info("获取id为【{}】的Story, roleId为【{}】的角色成员成功. 共有成员【{}】个.", storyId, roleId, members.size());
        return members;
    }

    /**
     * 保存角色成员映射
     * @param creatorId creatorId
     * @param storyId storyId
     * @param roleId roleId
     * @param memberSet memberSet
     */
    private void saveRoleMembers(Long creatorId, Long storyId, Long roleId, List<Long> memberSet) {
        for (Long memberId : memberSet) {
            StoryMember member = new StoryMember();
            member.setCreatorId(creatorId);
            member.setMemberId(memberId);
            member.setStoryId(storyId);
            member.setRoleId(roleId);
            this.memberRepository.saveAndFlush(member);
        }
    }

    /**
     * 添加指定角色的成员映射
     *
     * @param staffId staffId
     * @param storyId storyId
     * @param roleId roleId
     * @param addList addList
     */
    private void updateMembers(Long staffId, Long storyId, Long roleId, List<Long> addList) {
        List<Long> addMembers = new ArrayList<>();
        List<Long> updateMembers = new ArrayList<>();
        for (Long memberId : addList) {
            //如果映射不存在添加,已存在则修改isDeleted
            StoryMember member = this.memberRepository.findByStoryIdAndRoleIdAndMemberId(storyId, roleId, memberId);

            if (null == member) {
                addMembers.add(memberId);
            } else {
                updateMembers.add(memberId);
            }
        }
        //更新已存在映射的isDeleted为0
        updateIsDeleted(storyId, roleId, updateMembers, MdvnConstant.ZERO);
        //添加新映射
        saveRoleMembers(staffId, storyId, roleId, addMembers);
    }

    /**
     * 修改角色成员映射
     *
     * @param storyId storyId
     * @param roleId roleId
     * @param updateMembers updateMembers
     * @param isDeleted zero
     */
    private void updateIsDeleted(Long storyId, Long roleId, List<Long> updateMembers, Integer isDeleted) {
        this.memberRepository.updateIsDeleted(storyId, roleId, updateMembers, isDeleted);
    }
}

