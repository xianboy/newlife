package com.mdvns.mdvn.task.service;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.task.domain.CreateTaskRequest;
import com.mdvns.mdvn.task.domain.TestRequest;

public interface CreateService {
    //创建task
    RestResponse<?> create(CreateTaskRequest createRequest);

}
