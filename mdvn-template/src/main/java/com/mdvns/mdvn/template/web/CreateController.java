package com.mdvns.mdvn.template.web;

import com.mdvns.mdvn.common.bean.CustomFunctionLabelRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.template.domain.CreateTemplateRequest;
import com.mdvns.mdvn.template.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
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

    /**
     * 保存自定义过程方法模块: requirement/story
     * @param customRequest request
     * @param bindingResult request校验
     * @return restResponse
     */
    @PostMapping(value = "/customLabel")
    public RestResponse<?> create(@RequestBody @Validated CustomFunctionLabelRequest customRequest, BindingResult bindingResult) {
        //请求参数校验
        BindingResultUtil.brResolve(bindingResult);
        //请求处理
        return this.templateService.create(customRequest);
    }

}
