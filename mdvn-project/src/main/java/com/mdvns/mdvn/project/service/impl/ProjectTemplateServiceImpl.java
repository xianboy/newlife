package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.project.domain.entity.ProjectTemplate;
import com.mdvns.mdvn.project.repository.ProjectTemplateRepository;
import com.mdvns.mdvn.project.service.ProjectTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTemplateServiceImpl implements ProjectTemplateService {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectTagServiceImpl.class);

    @Autowired
    private ProjectTemplateRepository projectTemplateRepository;


    /**
     * 新建项目后，添加项目模板映射
     * @param projId
     * @param templates
     * @param creatorId
     * @return
     */
    @Override
    public List<ProjectTemplate> createProjectTemplate(Long projId, List<Long> templates, Long creatorId) {
        LOG.info("id为：[{}]的staff，准备创建id为：[{}] 的项目的模板id为：[{}]的映射信息.", creatorId, projId, templates.toString());
        List<ProjectTemplate> rojectTemplateList = new ArrayList<>();
        //遍历leaders构建ProjectStaff
        for (Long templateId : templates) {
            ProjectTemplate projectTemplate = new ProjectTemplate();
            projectTemplate.setCreatorId(creatorId);
            projectTemplate.setProjId(projId);
            projectTemplate.setTemplateId(templateId);
            projectTemplate.setCreateTime(new Timestamp(System.currentTimeMillis()));
            projectTemplate.setIsDeleted(MdvnConstant.ZERO);
            projectTemplate = this.projectTemplateRepository.saveAndFlush(projectTemplate);
            rojectTemplateList.add(projectTemplate);
        }
        LOG.info("保存映射信息完成");
        return rojectTemplateList;
    }
}