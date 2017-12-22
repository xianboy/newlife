package com.mdvns.mdvn.requirement.web;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.requirement.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Requirement查询Controller
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/requirements","/v1.0/requirements"})
public class RetrieveController {

    /*注入retrieve service*/
    @Autowired
    private RetrieveService retrieveService;

    /**
     * 查询指定project下的requirement列表:支持分页
     * @param singleCriterionRequest request
     * @param bindingResult bindingResult
     * @return
     */
    @PostMapping(value = "/retrieveList")
    public RestResponse<?> retrieveListByProjId(@RequestBody @Validated SingleCriterionRequest singleCriterionRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveListByProjId(singleCriterionRequest);
    }

    /**
     * 根据id获取详情
     * @param singleCriterionRequest request
     * @param bindingResult bindingResult
     * @return restResponse
     */
    @PostMapping(value = "/retrieveDetailById")
    public RestResponse<?> retrieveDetailById(@RequestBody @Validated SingleCriterionRequest singleCriterionRequest, BindingResult bindingResult) throws BusinessException {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveDetailById(singleCriterionRequest);
    }

}
