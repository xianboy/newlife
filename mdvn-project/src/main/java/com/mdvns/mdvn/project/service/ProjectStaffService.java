package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.domain.entity.ProjectStaff;

import java.util.List;

public interface ProjectStaffService {
    //新建项目时成功后，新建项目的leader映射
    List<ProjectStaff> createProjectStaff(Long projId, List<Long> leaders, Long creatorId);
}
