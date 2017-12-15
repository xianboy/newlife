package com.mdvns.mdvn.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@ConfigurationProperties(prefix = "url")
@Data
@Component
public class WebConfig {
    //获取requirement的url
    private String retrieveRequirementsUrl;
    //获取template的url
    private String retrieveTemplatesUrl;
    //获取tag的url
    private String retrieveTagsUrl;
    //获取leader的url
    private String retrieveLeadersUrl;


}
