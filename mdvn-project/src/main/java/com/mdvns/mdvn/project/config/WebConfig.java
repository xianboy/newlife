package com.mdvns.mdvn.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "url")
@Data
public class WebConfig {

    private String retrieveRequirementListUrl;


}
