package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.repository.ProjectRepository;
import com.mdvns.mdvn.project.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateSeviceImpl implements UpdateService {

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * 更新项目状态
     * @param status
     * @param id
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity<?> status(Integer status, Integer id) {

        Integer statusCode = this.projectRepository.updaetStatus(status, id);

        return RestResponseUtil.success(statusCode);
    }
}
