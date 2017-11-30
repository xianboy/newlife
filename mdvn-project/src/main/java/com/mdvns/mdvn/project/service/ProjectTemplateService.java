package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.domain.entity.ProjectTag;
import com.mdvns.mdvn.project.domain.entity.ProjectTemplate;

import java.util.List;

public interface ProjectTemplateService {
    //新建项目成功后，创建项目模板映射
    List<ProjectTemplate> createProjectTemplate(Long templateId, List<Long> templates, Long creatorId);
}
