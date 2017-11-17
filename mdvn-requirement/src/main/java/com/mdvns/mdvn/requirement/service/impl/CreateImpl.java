package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.requirement.domain.Requirement;
import com.mdvns.mdvn.requirement.repository.RequirementRepository;
import com.mdvns.mdvn.requirement.service.CreateSrevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CreateImpl implements CreateSrevice {

    private static final Logger LOG = LoggerFactory.getLogger(CreateImpl.class);

    @Autowired
    private RequirementRepository requirementRepository;


    /**
     * 新建requirement
     * @param requirement
     * @return
     */
    @Override
    public ResponseEntity<?> create(Requirement requirement) {
        //1.查询projId为指定id的requirement总数
        Long count = this.requirementRepository.countRequirementByProjId(requirement.getProjId());
        //2.将requirement的编号设值为：R+(count+1)
        requirement.setSerialNum("R"+(count+1));
        requirement.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //3.保存数据
        Requirement reqmnt = this.requirementRepository.saveAndFlush(requirement);
        LOG.info("requirement:{}创建成功!", reqmnt.getSerialNum());
        return RestResponseUtil.success(reqmnt);
    }
}
