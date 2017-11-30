package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.project.domain.entity.ProjectStaff;
import com.mdvns.mdvn.project.repository.ProjectStaffRepository;
import com.mdvns.mdvn.project.service.ProjectStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectStaffServiceImpl implements ProjectStaffService {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectTagServiceImpl.class);
    @Autowired
    private ProjectStaffRepository projectStaffRepository;

    /**
     * 新建ProjectStaff
     *
     * @param projId
     * @param leaders
     * @param creatorId
     * @return
     */
    @Override
    public List<ProjectStaff> createProjectStaff(Long projId, List<Long> leaders, Long creatorId) {
        LOG.info("id为：[{}]的staff，准备创建id为：[{}] 的项目的Staff id为：[{}]的映射信息.", creatorId, projId, leaders.toString());
        List<ProjectStaff> projectStaffList = new ArrayList<>();
        //遍历leaders构建ProjectStaff
        for (Long staffId : leaders) {
            ProjectStaff projectStaff = new ProjectStaff();
            projectStaff.setCreatorId(creatorId);
            projectStaff.setStaffId(staffId);
            projectStaff.setProjId(projId);
            projectStaff.setCreateTime(new Timestamp(System.currentTimeMillis()));
            projectStaff.setIsDeleted(MdvnConstant.ZERO);
            projectStaff = this.projectStaffRepository.save(projectStaff);
            projectStaffList.add(projectStaff);
        }
        LOG.info("保存映射信息完成");
        return projectStaffList;
    }
}
