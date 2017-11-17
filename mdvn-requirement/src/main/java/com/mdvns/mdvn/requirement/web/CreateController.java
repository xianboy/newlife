package com.mdvns.mdvn.requirement.web;

import com.mdvns.mdvn.requirement.domain.Requirement;
import com.mdvns.mdvn.requirement.service.CreateSrevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = {"/requirements","/v1.0/requirements"})
public class CreateController {

    @Autowired
    private CreateSrevice createSrevice;

    /**
     * 新建需求
     * @param requirement
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody Requirement requirement) {
        return this.createSrevice.create(requirement);
    }

}
