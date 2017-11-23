package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.domain.CreateProjectRequest;
import com.mdvns.mdvn.project.domain.Project;
import com.mdvns.mdvn.project.repository.ProjectRepository;
import com.mdvns.mdvn.project.service.CreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
public class CreateServiceImpl implements CreateService {
    /*实例化LOG常量*/
    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    /*注入project repository*/
    @Autowired
    private ProjectRepository projectRepository;

    /**
     *新建项目
     * @param createProjectRequest
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> create(CreateProjectRequest createProjectRequest) {
        //根据 createProjectRequest 构建project对象
        Project project = builProjectByRequest(createProjectRequest);
        //保存
        Project proj = this.projectRepository.saveAndFlush(project);
        LOG.info("新建项目的id为：{}, 编号为：{}",proj.getId(), proj.getSerialNum());
        return RestResponseUtil.success(proj);
    }

    /**
     * 根据 createProjectRequest 构建project对象
     * @param createProjectRequest
     * @return
     */
    private Project builProjectByRequest(CreateProjectRequest createProjectRequest) {
        Project project = new Project();
        //给项目编号赋值
        project.setSerialNum(buildSerialNum4Proj());
        //项目名称
        project.setName(createProjectRequest.getName());
        //描述
        project.setDescription(createProjectRequest.getDescription());
        //优先级
        project.setPriority(createProjectRequest.getPriority());
        //开始日期
        project.setStartDate(createProjectRequest.getStartDate());
        //结束日期
        project.setEndDate(createProjectRequest.getEndDate());
        //创建时间
        project.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //是否已删除
        project.setIsDeleted(MdvnConstant.ZERO);
        //可调整系数
        project.setContingency(createProjectRequest.getContingency());
        //创建人id
        project.setCreatorId(createProjectRequest.getCreatorId());
        //标签
        project.setTags(createProjectRequest.getTags());
        //负责人
        project.setLeaders(createProjectRequest.getLeaders());
        //模板
        project.setTemplates(createProjectRequest.getTemplates());
        //附件
        project.setAttaches(createProjectRequest.getAttaches());

        return project;
    }

    /**
     * 构建项目编号:
     * 1.查询表中的最大id  maxId
     * 2.serialNum = "P" + (maxId + 1)
     * @return
     */
    private String buildSerialNum4Proj() {
        //查询表中的最大id  maxId
        Long maxId = this.projectRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId==null) {
            maxId = 0L;
        }
        maxId += 1;
        return MdvnConstant.CONSTANT_P+maxId;
    }


}
