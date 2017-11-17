package com.mdvns.mdvn.project.service;

import com.mdvns.mdvn.project.exception.BusinessException;
import org.springframework.http.ResponseEntity;

public interface RetrieveService {
    /*获取项目列表: 支持分页*/
    ResponseEntity<?> retrieveProjects();
    /*获取指定id项目的详情*/
    ResponseEntity<?> retrieve(Long id) throws BusinessException;
}
