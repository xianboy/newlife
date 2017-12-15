package com.mdvns.mdvn.requirement.service;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;

public interface RetrieveService {

    /**
     * 查询指定project下的requirement列表:支持分页
     * @param singleArgRequest
     * @return
     */
    RestResponse<?> retrieveListByProjId(SingleCriterionRequest singleArgRequest);

    /*根据id获取详情*/
    RestResponse<?> retrieveDetailById(SingleCriterionRequest singleCriterionRequest) throws BusinessException;

}
