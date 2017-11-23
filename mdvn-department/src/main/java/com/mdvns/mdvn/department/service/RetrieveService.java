package com.mdvns.mdvn.department.service;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveDetailRequest;
import com.mdvns.mdvn.common.exception.BusinessException;

public interface RetrieveService {
    //查询所有数据：支持分页
    RestResponse<?> findAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest);

    //获取某个部门的详情
    RestResponse<?> retrieveDetail(RetrieveDetailRequest retrieveDetailRequest) throws BusinessException;
}
