package com.vcc.cem.api.tool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author czh
 * @date 2019-12-13
 */
@Component
@ConfigurationProperties(prefix="api")
@EnableConfigurationProperties
@Data
public class IPorperties {
    private Map<String,String> keys = new HashMap<>();

    private Map<String,String> weatherinfo = new HashMap<>();
    private Map<String,String> geocode = new HashMap<>();


}

