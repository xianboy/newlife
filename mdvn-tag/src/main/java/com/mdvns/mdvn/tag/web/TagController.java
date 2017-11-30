package com.mdvns.mdvn.tag.web;

import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.tag.domain.entity.Tag;
import com.mdvns.mdvn.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping(value = {"/tags", "/v1.0/tags"})
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 新建tag
     * @param tag
     * @return
     */
    @PostMapping(value = "/create")
    public RestResponse<?> create(@RequestBody @Validated Tag tag) throws BusinessException {
        return this.tagService.create(tag);
    }

    /**
     * 查询tag列表:支持分页
     * @param pageableQueryWithoutArgRequest
     * @return
     */
    @PostMapping(value = "/retrieveAll")
    public RestResponse<?> retrieveAll(@RequestBody @Validated PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        return this.tagService.retrieveAll(pageableQueryWithoutArgRequest);
    }

    /**
     * 获取指定name的部门详情
     * @param retrieveDetailRequest
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/retrieveByName")
    public RestResponse<?> retrieveDeatilByName(@RequestBody @Validated SingleCriterionRequest retrieveDetailRequest) throws BusinessException {
        return this.tagService.retrieveDetailByName(retrieveDetailRequest);
    }

}
