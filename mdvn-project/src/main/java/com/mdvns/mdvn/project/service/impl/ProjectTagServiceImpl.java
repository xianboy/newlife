package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.project.domain.entity.ProjectTag;
import com.mdvns.mdvn.project.repository.ProjectTagRepository;
import com.mdvns.mdvn.project.service.ProjectTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectTagServiceImpl implements ProjectTagService {
    private static final Logger LOG = LoggerFactory.getLogger(ProjectTagServiceImpl.class);

    @Autowired
    private ProjectTagRepository projectTagRepository;


    /**
     * 新建项目后，添加项目标签映射
     * @param projId
     * @param tags
     * @param creatorId
     * @return
     */
    @Override
    public List<ProjectTag> createProjectTag(Long projId, List<Long> tags, Long creatorId) {
        LOG.info("id为：[{}]的staff，准备创建id为：[{}] 的项目的标签id为：[{}]的映射信息.", creatorId, projId, tags.toString());
        List<ProjectTag> projectTagList = new ArrayList<>();
        //遍历leaders构建ProjectStaff
        for (Long tagId:tags) {
            ProjectTag projectTag = new ProjectTag();
            projectTag.setCreatorId(creatorId);
            projectTag.setProjId(projId);
            projectTag.setTagId(tagId);
            projectTag.setCreateTime(new Timestamp(System.currentTimeMillis()));
            projectTag.setIsDeleted(MdvnConstant.ZERO);
            projectTag = this.projectTagRepository.saveAndFlush(projectTag);
            projectTagList.add(projectTag);
        }
        LOG.info("保存映射信息完成");
        return projectTagList;
    }
}
