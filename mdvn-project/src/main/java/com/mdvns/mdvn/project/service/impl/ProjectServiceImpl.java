package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.domain.Project;
import com.mdvns.mdvn.project.repository.ProjectRepository;
import com.mdvns.mdvn.project.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ProjectServiceImpl implements ProjectService {
    /*实例化LOG常量*/
    private static final Logger LOG = LoggerFactory.getLogger(ProjectServiceImpl.class);

    /*注入project repository*/
    @Autowired
    private ProjectRepository projectRepository;

    /**
     *新建项目:
     * 1.查询表中的最大id  maxId
     * 2.将project的serialNum  赋值为 "P"+maxId
     * 3.保存数据
     * @param project
     * @return
     */
    @Override
    public ResponseEntity<?> create(Project project) {
        //查询表中的最大id  maxId
        Long maxId = this.projectRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId==null) {
            maxId = 0L;
        }
        //给maxId+1, 将project的serialNum  赋值为 "P"+maxId
        project.setSerialNum("P"+(maxId+1L));
        //赋默认值
        project.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //保存
        Project proj = this.projectRepository.saveAndFlush(project);
        LOG.info("新建项目的id为：{}, 编号为：{}",proj.getId(), proj.getSerialNum());
        return RestResponseUtil.success(proj);
    }


}
