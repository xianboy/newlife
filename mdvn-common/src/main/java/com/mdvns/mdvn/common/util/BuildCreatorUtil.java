package com.mdvns.mdvn.common.util;

import com.mdvns.mdvn.common.bean.RetrieveBaseInfoRequest;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class BuildCreatorUtil {


    private static TerseInfo buildCreator(Long creatorId) {
        List<Long> ids = new ArrayList<>();
        ids.add(creatorId);
        //构建request
        RetrieveBaseInfoRequest baseInfoRequest = new RetrieveBaseInfoRequest(creatorId, ids);

        RestTemplate restTemplate = new RestTemplate();




        return null;
    }
}
