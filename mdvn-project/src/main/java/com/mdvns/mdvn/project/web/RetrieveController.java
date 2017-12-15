package com.mdvns.mdvn.project.web;

import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.project.service.RetrieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 查询controller
 */
@RestController
@CrossOrigin
@RequestMapping(value = {"/projects", "/v1.0/projects"})
public class RetrieveController {
    /*注入retrieve service*/
    @Autowired
    private RetrieveService retrieveService;

    /**
     * 查询项目列表:支持分页
     * @param pageableQueryWithoutArgRequest
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/retrieveAll")
    public RestResponse<?> retrieveAll(@RequestBody @Validated PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveAll(pageableQueryWithoutArgRequest);
    }

    /**
     * 获取指定id的项目详情
     * @param retrieveDetailRequest
     * @return
     */
    @PostMapping(value = "/retrieveById")
    public RestResponse<?> retrieveDetailById(@RequestBody @Validated SingleCriterionRequest retrieveDetailRequest, BindingResult bindingResult) throws BusinessException {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveDetailById(retrieveDetailRequest);
    }

}
