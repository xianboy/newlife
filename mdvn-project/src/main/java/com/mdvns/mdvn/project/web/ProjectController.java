package com.mdvns.mdvn.project.web;

import com.mdvns.mdvn.project.domain.Project;
import com.mdvns.mdvn.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * 添加cotroller
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/projects","/v1.0/projects"})
public class ProjectController {

    @Autowired
    private ProjectService service;

    /**
     * 新建项目
     * @param project
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Project project) {
        return this.service.create(project);
    }



}
