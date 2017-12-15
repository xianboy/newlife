package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.bean.UpdateBasicInfoRequest;
import com.mdvns.mdvn.common.bean.UpdateStatusRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.domain.UpdateOtherInfoRequest;
import com.mdvns.mdvn.project.domain.entity.Project;
import com.mdvns.mdvn.project.repository.ProjectRepository;
import com.mdvns.mdvn.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    private ProjectRepository projectRepository;


    @Autowired
    private TagService projectTagService;

    @Autowired
    private LeaderService projectStaffService;

    @Autowired
    private TemplateService projectTemplateService;

    @Autowired
    private RetrieveService retrieveService;

    /**
     * 更新项目状态
     *
     * @param updateStatusRequest 更新参数对象
     * @return status
     */
    @Override
    @Modifying
    @Transactional
    public RestResponse<?> updateStatus(UpdateStatusRequest updateStatusRequest) {
        //更新项目状态
        Project project = this.projectRepository.updateStatus(updateStatusRequest.getStatus(), updateStatusRequest.getHostId());
        //构建response并返回
        return RestResponseUtil.success(project.getStatus());
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
        Long projId = updateBasicInfoRequest.getHostId();
        //获取name和desc
        String name = updateBasicInfoRequest.getFirstPart();
        String desc = updateBasicInfoRequest.getSecondPart();
        Integer updated = null;
        //如果那么为空, 则更新描述
        if (StringUtils.isEmpty(name)) {
            updated = this.projectRepository.updateDesc(desc, projId);
        } else if (StringUtils.isEmpty(desc)) {
            //如果描述为空,则更新name
            updated = this.projectRepository.updateName(name, projId);
        } else {
            //如果都不为空, 同时更新名称和描述
            updated = this.projectRepository.updateNameAndDesc(name, desc, projId);
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
        Project project = buildProjectByRequest(updateRequest);
        return retrieveByProjId(updateRequest.getStaffId(), updateRequest.getHostId());
    }

    /**
     * 根据request构建project对象
     *
     * @param updateRequest 更新对象
     * @return project
     */
    private Project buildProjectByRequest(UpdateOtherInfoRequest updateRequest) throws BusinessException {
        //项目id
        Long projId = updateRequest.getHostId();
        //根据id查询项目
        Project project = this.projectRepository.findOne(projId);
        //如果project不存在, 抛出异常
        MdvnCommonUtil.notExistingError(project, "id", projId.toString());
        //优先级
        if (null != updateRequest.getPriority()) {
            project.setPriority(updateRequest.getPriority());
        }
        //开始时间
        if (null != updateRequest.getStartDate()) {
            project.setStartDate(updateRequest.getStartDate());
        }
        //结束时间
        if (null != updateRequest.getEndDate()) {
            project.setEndDate(updateRequest.getEndDate());
        }
        //可调整系数
        if (null != updateRequest.getContingency()) {
            project.setContingency(updateRequest.getContingency());
        }
        project = this.projectRepository.saveAndFlush(project);
        //更新标签
        if (null != updateRequest.getTags()) {
            this.projectTagService.updateTag(updateRequest.getStaffId(), projId, updateRequest.getTags());
        }
        //更新负责人
        if (null != updateRequest.getLeaders()) {
            this.projectStaffService.updateProjectLeader(updateRequest.getStaffId(), projId, updateRequest.getLeaders());
        }
        //更新模板
        if (null != updateRequest.getTags()) {
            this.projectTemplateService.updateProjectTemplate(updateRequest.getStaffId(), projId, updateRequest.getTags());
        }
        return project;
    }

    /**
     * @param staffId staffId
     * @param projId projId
     * @return RestResponse
     */
    private RestResponse<?> retrieveByProjId(Long staffId, Long projId) throws BusinessException {
        return this.retrieveService.retrieveDetailById(new SingleCriterionRequest(staffId, projId.toString()));
    }


}
