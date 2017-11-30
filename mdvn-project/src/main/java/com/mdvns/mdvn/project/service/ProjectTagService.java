package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.domain.entity.ProjectTag;

import java.util.List;

public interface ProjectTagService {
    //新建项目成功后，创建项目标签映射
    List<ProjectTag> createProjectTag(Long id, List<Long> tags, Long creatorId);

}
