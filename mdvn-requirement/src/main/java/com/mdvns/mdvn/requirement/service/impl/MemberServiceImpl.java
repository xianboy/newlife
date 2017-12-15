package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.bean.MemberRequest;
import com.mdvns.mdvn.common.bean.model.RoleMember;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestTemplateUtil;
import com.mdvns.mdvn.requirement.config.WebConfig;
import com.mdvns.mdvn.requirement.domain.entity.RequirementMember;
import com.mdvns.mdvn.requirement.repository.MemberRepository;
import com.mdvns.mdvn.requirement.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MemberServiceImpl implements MemberService {

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceImpl.class);

    @Resource
    private MemberRepository memberRepository;

    @Resource
    private WebConfig webConfig;

    /**
     * 保存需求成员映射
     *
     * @param creatorId
     * @param requirementId
     * @param members
     */
    @Override
    public Integer handleMembers(Long creatorId, Long requirementId, List<MemberRequest> members) {
        //遍历members构建并保存RequirementMember
        Integer size = 0;
        for (MemberRequest memberRequest : members) {
            //遍历保存每个模板角色下的member
            size += memberRequest.getAddList().size();
            saveRoleMembers(creatorId, requirementId, memberRequest.getRoleId(), memberRequest.getAddList());
        }
        LOG.info("保存成员映射信息完成");
        return size;
    }

    /**
     *保存角色成员映射
     * @param creatorId creatorId
     * @param requirementId requirementId
     * @param roleId roleId
     * @param memberSet memberSet
     */
    private void saveRoleMembers(Long creatorId, Long requirementId, Long roleId, List<Long> memberSet) {
        for (Long memberId : memberSet) {
            RequirementMember member = new RequirementMember();
            member.setCreatorId(creatorId);
            member.setMemberId(memberId);
            member.setRequirementId(requirementId);
            member.setRoleId(roleId);
            this.memberRepository.saveAndFlush(member);
        }
    }

    /**
     * 获取指定requirement的成员信息
     *
     * @param staffId       staffId
     * @param requirementId requirementId
     * @return list
     */
    public List<RoleMember> getRoleMembers(Long staffId, Long requirementId) throws BusinessException {
        //查询指定requirement的所有角色id
        List<Long> roles = this.memberRepository.findRoleIdDistinctByRequirementId(requirementId);
        //获取角色id和name
        List<TerseInfo> tmplRoles = getRoles(staffId, roles);
        //如果角色信息为空, 抛出异常
        if (null == tmplRoles || tmplRoles.isEmpty()) {
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
            List<TerseInfo> members = getMembers(staffId, requirementId, tmplRole.getId());
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
        return roleMembers;
    }

    /**
     * 修改角色成员
     *
     * @param staffId       staffId
     * @param requirementId requirementId
     * @param roleMembers   roleMembers
     */
    @Override
    public void updateRoleMembers(Long staffId, Long requirementId, List<MemberRequest> roleMembers) {
        //遍历 roleMembers, 处理每一个角色对应的成员修改
        for (MemberRequest memberRequest : roleMembers) {
            //删除成员
            List<Long> removeList = memberRequest.getRemoveList();
            if (!(null == removeList || removeList.isEmpty())) {
                this.memberRepository.updateIsDeleted(requirementId, memberRequest.getRoleId(), memberRequest.getRemoveList(), MdvnConstant.ONE);
            }
            //新增成员
            List<Long> addList = memberRequest.getAddList();
            if (!(null == addList || addList.isEmpty())) {
                updateMembers(staffId, requirementId, memberRequest.getRoleId(), memberRequest.getAddList());
            }

        }
    }

    /**
     * 添加指定角色的成员映射
     * @param staffId staffId
     * @param requirementId requirementId
     * @param roleId roleId
     * @param addList addList
     */
    private void updateMembers(Long staffId, Long requirementId, Long roleId, List<Long> addList) {
        List<Long> addMembers = new ArrayList<>();
        List<Long> updateMembers = new ArrayList<>();
        for (Long memberId : addList) {
            //如果映射不存在添加,已存在则修改isDeleted
            RequirementMember rm = this.memberRepository.findByRequirementIdAndRoleIdAndMemberId(requirementId, roleId, memberId);

            if (null == rm) {
                addMembers.add(memberId);
            } else {
                updateMembers.add(memberId);
            }
        }
        //更新已存在映射的isDeleted为0
        updateIsDeleted(requirementId,roleId, updateMembers, MdvnConstant.ZERO);
        //添加新映射
        saveRoleMembers(staffId, requirementId, roleId, addMembers);
    }

    /**
     * 修改角色成员映射
     * @param requirementId requirementId
     * @param roleId roleId
     * @param updateMembers updateMembers
     * @param isDeleted zero
     */
    private void updateIsDeleted(Long requirementId, Long roleId, List<Long> updateMembers, Integer isDeleted) {
        this.memberRepository.updateIsDeleted(requirementId,roleId,updateMembers,isDeleted);
    }

    /**
     * 根据roleId查询角色id和name
     *
     * @param staffId staffId
     * @param roles   roles
     * @return list
     */
    private List<TerseInfo> getRoles(Long staffId, List<Long> roles) throws BusinessException {
        //查询role的url
        String retrieveRoleUrl = webConfig.getRetrieveRoleUrl();
        //调用Template模块查询role
        return RestTemplateUtil.retrieveBasicInfo(staffId, roles, retrieveRoleUrl);
    }

    /**
     * 查询指定requirementId 和 roleId的成员信息
     *
     * @param staffId       staffId
     * @param requirementId requirementId
     * @param roleId        roleId
     * @return lsit
     * @throws BusinessException exception
     */
    public List<TerseInfo> getMembers(Long staffId, Long requirementId, Long roleId) throws BusinessException {
        //获取成员id和name
        List<Long> ids = this.memberRepository.findMemberIdByRoleIdAndRequirementId(requirementId, roleId);
        //获取member的url
        String retrieveMembersUrl = webConfig.getRetrieveMembersUrl();
        //调用staff模块获取成员信息
        List<TerseInfo> members = RestTemplateUtil.retrieveBasicInfo(staffId, ids, retrieveMembersUrl);
        MdvnCommonUtil.emptyList(members, ErrorEnum.STAFF_NOT_EXISTS, "Id为【" + ids.toString() + "】的用户不存在.");
        return members;
    }

}
