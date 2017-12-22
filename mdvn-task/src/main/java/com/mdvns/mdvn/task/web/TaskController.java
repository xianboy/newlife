package com.mdvns.mdvn.task.web;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.task.domain.CreateTaskRequest;
import com.mdvns.mdvn.task.service.CreateService;
import com.mdvns.mdvn.task.service.RetrieveService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@CrossOrigin
@RequestMapping(value = {"/task", "/v1.0/tasks"})
public class TaskController {

    @Resource
    private CreateService createService;

    @Resource
    private RetrieveService retrieveService;

    /**
     * 创建
     * @param createRequest createRequest
     * @param bindingResult bindingResult
     * @return RestResponse
     */
    @PostMapping(value = "/create")
    public RestResponse<?> create(@RequestBody CreateTaskRequest createRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.createService.create(createRequest);
    }

    /**
     * 根据id获取详情
     * @param retrieveRequest retrieveRequest
     * @param bindingResult bindingResult
     * @return RestResponse
     */
    @PostMapping(value = "/retrieveDetailById")
    public RestResponse<?> retrieveDetailById(@RequestBody @Validated SingleCriterionRequest retrieveRequest, BindingResult bindingResult) throws BusinessException {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveDetailById(retrieveRequest);
    }

}
