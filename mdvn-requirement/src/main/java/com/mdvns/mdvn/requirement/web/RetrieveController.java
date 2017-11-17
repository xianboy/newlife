package com.mdvns.mdvn.requirement.web;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveListByProjIdRequest;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.requirement.service.RetrieveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
     * @param byProjIdRequest request
     * @param bindingResult bindingResult
     * @return
     */
    @PostMapping(value = "/retrieveList")
    public ResponseEntity<?> retrieveListByProjId(@RequestBody @Validated RetrieveListByProjIdRequest byProjIdRequest, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveListByProjId(byProjIdRequest);
    }

    /*
     * 工具方法：查询列表(支持分页)
     *
    @PostMapping(value = "/retrieveList")
    public ResponseEntity<?> retrieveList(@RequestBody RetrieveListRequest listRequest) {
        return this.retrieveService.retrieveList(listRequest);
    }*/

}
