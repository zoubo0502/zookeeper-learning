package com.bo.zou.zookeeper_config.service;

import com.bo.zou.zookeeper_config.configuration.ZKClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigService {

    private final Logger logger = LoggerFactory.getLogger(ConfigService.class);
    private static final String CONFIG_PATH = "/config";

    @Autowired
    private ZKClient zkClient;

    public void updateConfig(String data)  {
        try {
            zkClient.setData(CONFIG_PATH, data);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
    }
}
