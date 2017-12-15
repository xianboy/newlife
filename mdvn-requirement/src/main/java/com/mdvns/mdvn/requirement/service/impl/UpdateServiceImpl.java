package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.UpdateBasicInfoRequest;
import com.mdvns.mdvn.common.bean.UpdateStatusRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.requirement.domain.UpdateOtherInfoRequest;
import com.mdvns.mdvn.requirement.domain.entity.Requirement;
import com.mdvns.mdvn.requirement.repository.RequirementRepository;
import com.mdvns.mdvn.requirement.service.MemberService;
import com.mdvns.mdvn.requirement.service.TagService;
import com.mdvns.mdvn.requirement.service.UpdateService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Resource
    private RequirementRepository requirementRepository;

    @Resource
    private TagService tagService;

    @Resource
    private MemberService memberService;

    /**
     * 更新状态
     *
     * @param updateStatusRequest request
     * @return restResponse
     */
    @Override
    @Modifying
    @Transactional
    public RestResponse<?> updateStatus(UpdateStatusRequest updateStatusRequest) {
        //更新项目状态
        Requirement requirement = this.requirementRepository.updateStatus(updateStatusRequest.getStatus(), updateStatusRequest.getHostId());
        //构建response并返回
        return RestResponseUtil.success(requirement.getStatus());
    }

    /**
     * 修改基础信息
     *
     * @param updateBasicInfoRequest 更新参数对象
     * @return 更新数据条数
     */
    @Override
    @Transactional
    @Modifying
    public RestResponse<?> updateBasicInfo(UpdateBasicInfoRequest updateBasicInfoRequest) {

        //获取更新对象的id
        Long requirementId = updateBasicInfoRequest.getHostId();
        //获取summary和desc
        String summary = updateBasicInfoRequest.getFirstPart();
        String desc = updateBasicInfoRequest.getSecondPart();
        Integer updated;
        //如果那么为空, 则更新描述
        if (StringUtils.isEmpty(summary)) {
            updated = this.requirementRepository.updateDescription(desc, requirementId);
        } else if (StringUtils.isEmpty(desc)) {
            //如果描述为空,则更新name
            updated = this.requirementRepository.updateSummary(summary, requirementId);
        } else {
            //如果都不为空, 同时更新名称和描述
            updated = this.requirementRepository.updateBoth(summary, desc, requirementId);
        }
        return RestResponseUtil.success(updated);
    }

    /**
     * 更新其他信息
     *
     * @param updateRequest 参数对象
     * @return 更新数据条数
     */
    @Override
    @Transactional
    public RestResponse<?> updateOtherInfo(UpdateOtherInfoRequest updateRequest) throws BusinessException {
        //根据request构建project对象
        Requirement requirement = buildByRequest(updateRequest);


        return null;
    }

    /**
     * 构建requirement
     *
     * @param updateRequest request
     * @return requirement
     */
    private Requirement buildByRequest(UpdateOtherInfoRequest updateRequest) throws BusinessException {
        //需求id
        Long requirementId = updateRequest.getRequirementId();
        //根据id查询项目
        Requirement requirement = this.requirementRepository.findOne(requirementId);
        //如果requirement不存在, 抛出异常
        MdvnCommonUtil.notExistingError(requirement, ErrorEnum.REQUIREMENT_NOT_EXISTS, "Id为【" + requirementId + "】的需求不存在.");
        //优先级
        if (null != updateRequest.getPriority()) {
            requirement.setPriority(updateRequest.getPriority());
        }
        //开始时间
        if (null != updateRequest.getStartDate()) {
            requirement.setStartDate(updateRequest.getStartDate());
        }
        //结束时间
        if (null != updateRequest.getEndDate()) {
            requirement.setEndDate(updateRequest.getEndDate());
        }
        requirement = this.requirementRepository.saveAndFlush(requirement);
        //更新标签
        if (null != updateRequest.getTags()) {
            this.tagService.updateTags(updateRequest.getStaffId(), requirementId, updateRequest.getTags());
        }
        //更新RoleMember
        if (null != updateRequest.getRoleMembers()) {
            this.memberService.updateRoleMembers(updateRequest.getStaffId(), requirementId, updateRequest.getRoleMembers());
        }

        return requirement;
    }


}
