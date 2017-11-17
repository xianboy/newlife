package com.mdvns.mdvn.project.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdvns.mdvn.common.bean.PageableResponse;
import com.mdvns.mdvn.common.bean.Requirement;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveListByProjIdRequest;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.config.WebConfig;
import com.mdvns.mdvn.project.domain.Project;
import com.mdvns.mdvn.project.exception.BusinessException;
import com.mdvns.mdvn.project.repository.ProjectRepository;
import com.mdvns.mdvn.project.service.RetrieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    /*repository 注入*/
    @Autowired
    private ProjectRepository projectRepository;

    /*注入 webconfig*/
    private WebConfig webConfig;

    @Override
    public ResponseEntity<?> retrieveProjects() {

        return null;
    }

    /**
     * 获取指定id的项目详情
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<?> retrieve(Long id) throws BusinessException {
        Project proj = this.projectRepository.findOne(id);
        //获取需求列表,
        List<Requirement> requirements = getRequirements(id);
        LOG.info("Id:{}", requirements.get(0).getId());
        return RestResponseUtil.success(requirements);
    }

    /**
     * 获取指定项目id的需求列表
     * @param id
     * @return
     */
    private List<Requirement> getRequirements(Long id) throws BusinessException {

//        String retrieveRequirementListUrl = webConfig.getRetrieveRequirementListUrl();


        RestTemplate restTemplate = new RestTemplate();
        //构建查询参数
        RetrieveListByProjIdRequest byProjIdRequest = new RetrieveListByProjIdRequest();
        byProjIdRequest.setProjId(id);
        //构建url
        String url = "http://localhost:20002/mdvn-requirement/requirements/retrieveList";
        ParameterizedTypeReference<RestResponse<PageableResponse<Requirement>>> typeReference = new ParameterizedTypeReference<RestResponse<PageableResponse<Requirement>>>() {
        };
        HttpEntity<?> requestEntity = new HttpEntity<>(byProjIdRequest);
        ResponseEntity<RestResponse<PageableResponse<Requirement>>> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, typeReference);
        RestResponse<PageableResponse<Requirement>> restResponse = responseEntity.getBody();
//        RestResponse<?> restResponse = restTemplate.postForObject(url, byProjIdRequest, RestResponse.class);
        /*if (!(restResponse.getCode().equals("000"))) {
            LOG.error("获取指定项目的需求列表失败: {}", restResponse.getMsg());
            throw new BusinessException(restResponse.getCode(), restResponse.getMsg());
        }*/
        LOG.info("response code：{}.", restResponse.getCode());
        LOG.info("response msg：{}.", restResponse.getMsg());
        return restResponse.getData().getContent();
    }

    /**
     * @param json 准备转换json
     * @param clazz 集合元素类型
     * @return
     * @description json字符串转换成对象集合
     * @author paul
     * @date 2017年8月12日 下午1:28:27
     * @update 2017年8月12日 下午1:28:27
     * @version V1.0
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseJsonList(String json, Class<T> clazz) {
        try {
            JavaType javaType = getCollectionType(ArrayList.class, clazz);
            return (List<T>) INSTANCE.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param collectionClass 集合类
     * @param elementClasses 集合元素类
     * @return
     * @description 获取泛型的ColloectionType
     * @author paul
     * @date 2017年8月12日 下午2:17:38
     * @update 2017年8月12日 下午2:17:38
     * @version V1.0
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return INSTANCE.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
    private static ObjectMapper INSTANCE = new ObjectMapper();
}
