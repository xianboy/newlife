package com.mdvns.mdvn.requirement.service;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveListByProjIdRequest;
import org.springframework.http.ResponseEntity;

public interface RetrieveService {

    /**
     * 查询指定project下的requirement列表:支持分页
     * @param singleArgRequest
     * @return
     */
    ResponseEntity<?> retrieveListByProjId(RetrieveListByProjIdRequest singleArgRequest);

//    ResponseEntity<?> retrieveList(RetrieveListRequest listRequest);


}
