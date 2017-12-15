package com.mdvns.mdvn.template.service;

import com.mdvns.mdvn.common.bean.*;

public interface RetrieveService {
    //根据industryId查询模板
    RestResponse<?> retrieveByIndustryId(SingleCriterionRequest singleCriterionRequest);

    //分页查询所有数据
    RestResponse<?> retrieveAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest);

    //根据id集合查询基本信息
    RestResponse<?> retrieveBaseInfo(RetrieveBaseInfoRequest retrieveBaseInfoRequest);

    //根据id集合查询templateRole的基本信息
    RestResponse<?> retrieveRoleBaseInfo(RetrieveBaseInfoRequest retrieveBaseInfoRequest);

    //根据id集合查询FunctionLabel的基本信息
    RestResponse<?> retrieveLabel(RetrieveBaseInfoRequest retrieveBaseInfoRequest);
}
