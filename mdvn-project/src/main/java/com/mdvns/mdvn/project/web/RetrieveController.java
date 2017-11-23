package com.mdvns.mdvn.project.web;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.util.BindingResultUtil;
import com.mdvns.mdvn.project.exception.BusinessException;
import com.mdvns.mdvn.project.service.RetrieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    /*初始化日志常量*/
    private static final Logger LOG = LoggerFactory.getLogger(RetrieveController.class);

    /*注入retrieve service*/
    @Autowired
    private RetrieveService retrieveService;

    /**
     * 获取project列表:支持分页
     * @return
     */
    @PostMapping(value = "/list")
    public ResponseEntity<?> retrieveProjects(@RequestBody @Validated PageableCriteria pageableCriteria, BindingResult bindingResult) {
        BindingResultUtil.brResolve(bindingResult);
        return this.retrieveService.retrieveProjects();
    }

    /**
     * 获取指定id的项目详情
     * @param id
     * @return
     */
    @PostMapping(value = "/retrieve/{id}")
    public ResponseEntity<?> retrieve(@PathVariable Long id) throws BusinessException {
        return this.retrieveService.retrieve(id);
    }
}
