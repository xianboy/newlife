package com.mdvns.mdvn.project.web;

import com.mdvns.mdvn.project.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 更新controller
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/projects", "/v1.0/projects"})
public class UpdateCotroller {

    /*自动注入update service*/
    @Autowired
    private UpdateService updateService;

    @PostMapping(value = "/update/{status}/{id}")
    private ResponseEntity<?> status(@PathVariable Integer id, @PathVariable Integer status) {
        return this.updateService.status(status, id);
    }



}
