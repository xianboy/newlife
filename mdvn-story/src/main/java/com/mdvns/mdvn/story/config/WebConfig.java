package com.mdvns.mdvn.story.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "url")
@Data
@Component
public class WebConfig {

    //自定义过程方法url
    private String customLabelUrl;

    //根据name和hostSerialNo查询FunctionLabel的url
    private String retrieveLabelByNameAndHostUrl;

    //获取标签url
    private String retrieveTagsUrl;

    //获取成员url
    private String retrieveMembersUrl;

    //获取过程方法url
    private String retrieveLabelUrl;

    //获取TemplateRole的url
    private String retrieveRoleUrl;

}
