package com.mdvns.mdvn.tag.service;

import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveBaseInfoRequest;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.tag.domain.entity.Tag;

public interface TagService {
    //新建tag
    RestResponse<?> create(Tag tag) throws BusinessException;

    //查询所有:支持分页
    RestResponse<?> retrieveAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest);

    //查询name包含指定字符串的标签
    RestResponse<?> retrieveDetailByName(SingleCriterionRequest retrieveDetailRequest) throws BusinessException;
    //获取指定id集合的基本信息
    RestResponse<?> retrieveBaseInfo(RetrieveBaseInfoRequest retrieveBaseInfoRequest);
}
