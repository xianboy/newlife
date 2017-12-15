package com.mdvns.mdvn.template.web;

import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveBaseInfoRequest;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.template.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping(value = {"/templates", "/v1.0/templates"})
public class RetrieveController {

    @Resource
    private RetrieveService retrieveService;


    /**
     * 根据行业类型查询
     * @param singleCriterionRequest
     * @return
     */
    @PostMapping(value = "/retrieveByIndustry")
    public RestResponse<?> retrieveByIndustryId(@RequestBody @Validated SingleCriterionRequest singleCriterionRequest) {
        return this.retrieveService.retrieveByIndustryId(singleCriterionRequest);
    }

    /**
     * 分页查询所有数据
     * @param pageableQueryWithoutArgRequest
     * @return
     */
    @PostMapping(value = "/retrieveAll")
    public RestResponse<?> retrieveAll(@RequestBody @Validated PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        return this.retrieveService.retrieveAll(pageableQueryWithoutArgRequest);
    }

    /**
     * 根据指定id集合查询id和name
     * @param retrieveBaseInfoRequest
     * @return
     */
    @PostMapping(value = "/retrieveBaseInfo")
    public RestResponse<?> retrieveBaseInfo(@RequestBody @Validated RetrieveBaseInfoRequest retrieveBaseInfoRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveBaseInfo(retrieveBaseInfoRequest);
    }

    /**
     * 根据指定id集合查询TemplateROle的id和name
     * @param retrieveBaseInfoRequest
     * @return
     */
    @PostMapping(value = "/retrieveRoleBaseInfo")
    public RestResponse<?> retrieveRoleBaseInfo(@RequestBody @Validated RetrieveBaseInfoRequest retrieveBaseInfoRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveRoleBaseInfo(retrieveBaseInfoRequest);
    }

    /**
     * 根据指定id集合查询FunctionLabel
     * @param retrieveBaseInfoRequest request
     * @param bindingResult bindingResult
     * @return restResponse
     */
    @PostMapping(value = "/retrieveLabel")
    public RestResponse<?> retrieveLabel(@RequestBody @Validated RetrieveBaseInfoRequest retrieveBaseInfoRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveLabel(retrieveBaseInfoRequest);
    }

}
