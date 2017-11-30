package com.mdvns.mdvn.staff.web;

import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.staff.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = {"/staff", "/v1.0/staff"})
public class RetrieveController {

    @Autowired
    private RetrieveService retrieveService;

    /**
     * 获取指定id的Staff详情
     * @param singleCriterionRequest
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/retrieveById")
    public RestResponse<?> retrieveDetailById(@RequestBody @Validated SingleCriterionRequest singleCriterionRequest, BindingResult bindingResult) throws BusinessException {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveDetailById(singleCriterionRequest);
    }

    /**
     * 获取staff列表
     * @return
     */
    @PostMapping(value = "/")
    public RestResponse<?> retrieveAll(@RequestBody @Validated PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveAll(pageableQueryWithoutArgRequest);
    }


}
