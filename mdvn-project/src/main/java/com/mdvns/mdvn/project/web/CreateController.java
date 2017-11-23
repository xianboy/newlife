package com.mdvns.mdvn.project.web;

import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.project.domain.CreateProjectRequest;
import com.mdvns.mdvn.project.domain.Project;
import com.mdvns.mdvn.project.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 添加cotroller
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/projects","/v1.0/projects"})
public class CreateController {

    @Autowired
    private CreateService service;

    /**
     * 新建项目
     * @param createProjectRequest
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody @Validated CreateProjectRequest createProjectRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.service.create(createProjectRequest);
    }



}
