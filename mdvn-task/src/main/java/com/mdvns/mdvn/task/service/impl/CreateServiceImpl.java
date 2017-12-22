package com.mdvns.mdvn.task.service.impl;

import com.mdvns.mdvn.common.bean.CustomDeliveryRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.task.config.WebConfig;
import com.mdvns.mdvn.task.domain.CreateTaskRequest;
import com.mdvns.mdvn.task.domain.TestRequest;
import com.mdvns.mdvn.task.domain.entity.Task;
import com.mdvns.mdvn.task.repository.TaskRepository;
import com.mdvns.mdvn.task.service.CreateService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.LinkedHashMap;

@Service
public class CreateServiceImpl implements CreateService {

    @Resource
    private TaskRepository repository;

    @Resource
    private WebConfig webConfig;

    /**
     * 创建task
     *
     * @param createRequest request
     * @return RestResponse
     */
    @Override
    public RestResponse<?> create(CreateTaskRequest createRequest) {
        //根据request构建task
        Task task = buildByRequest(createRequest);
        //保存
        task = this.repository.saveAndFlush(task);
        return RestResponseUtil.success(task);
    }

    /**
     * 构建task
     *
     * @param createRequest request
     * @return Task
     */
    private Task buildByRequest(CreateTaskRequest createRequest) {
        Task task = new Task();
        task.setCreatorId(createRequest.getCreatorId());
        String serialNo = buildSerialNo();
        task.setSerialNo(serialNo);
        task.setHostSerialNo(createRequest.getHostSerialNo());
        task.setDescription(createRequest.getDescription());
        task.setDeliveryId(buildDelivery(createRequest.getDelivery(), createRequest.getCreatorId(), serialNo));
        task.setStartDate(createRequest.getStartDate());
        task.setEndDate(createRequest.getEndDate());
        return task;
    }

    /**
     * 构建交付件
     * @param delivery delivery
     * @param creatorId creatorId
     * @param hostSerialNo hostSerialNo
     * @return Long
     */
    private Long buildDelivery(Object delivery, Long creatorId, String hostSerialNo) {
        //如果是Integer类型, 就是已存在的交付件的id
        if (delivery instanceof Integer) {
            return Long.valueOf(delivery.toString());
        } else {
            //如果是CustomDeliverable类型, 则自定义交付件
            return customDelivery(delivery, creatorId, hostSerialNo);
        }

    }

    /**
     * 自定义交付件
     * @param delivery delivery
     * @param creatorId creatorId
     * @param hostSerialNo hostSerialNo
     * @return Long
     */
    private Long customDelivery(Object delivery, Long creatorId, String hostSerialNo) {
        CustomDeliveryRequest customDelivery = new CustomDeliveryRequest();
        LinkedHashMap map = (LinkedHashMap) delivery;
        customDelivery.setName(map.get("name").toString());
        customDelivery.setTypeId(Integer.valueOf(map.get("typeId").toString()));
        customDelivery.setCreatorId(creatorId);
        customDelivery.setHostSerialNo(hostSerialNo);
        RestTemplate restTemplate = new RestTemplate();
        String customDeliveryUrl = webConfig.getCustomDeliveryUrl();
        return restTemplate.postForObject(customDeliveryUrl, customDelivery, Long.class);
    }

    /**
     * 构建编号
     *
     * @return String
     */
    private String buildSerialNo() {
        //查询表中的最大id  maxId
        Long maxId = this.repository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = Long.valueOf(MdvnConstant.ZERO);
        }
        maxId += 1;
        return MdvnConstant.T + maxId;
    }
}
