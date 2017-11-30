package com.mdvns.mdvn.template.web;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.template.domain.CreateTemplateRequest;
import com.mdvns.mdvn.template.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = {"/templates", "/v1.0/templates"})
public class CreateController {

    @Autowired
    private CreateService templateService;


    /**
     * 新建
     * @param
     * @return
     */
    @PostMapping(value = "/create")
    public RestResponse<?> create(@RequestBody @Validated CreateTemplateRequest createRequest) throws BusinessException {
        return this.templateService.create(createRequest);
    }

}
