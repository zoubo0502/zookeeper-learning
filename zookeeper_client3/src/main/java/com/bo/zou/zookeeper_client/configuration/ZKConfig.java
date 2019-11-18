package com.bo.zou.zookeeper_client.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@PropertySources(value = {@PropertySource("classpath:zookeeper.properties")})
public class ZKConfig {
    private final Logger logger = LoggerFactory.getLogger(ZKConfig.class);

    @Autowired
    private Environment environment;

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public ZKClient zkClient() {
        return new ZKClient(environment.getRequiredProperty("zookeeper.server"));
    }


}
