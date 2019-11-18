package com.bo.zou.zookeeper_client.client;

import com.bo.zou.zookeeper_client.configuration.ZKClient;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class Client implements ApplicationRunner {

    private static final String SERVICE_PATH = "/service/provider";

    @Autowired
    private ZKClient zkClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("provider started");
        zkClient.createNode(SERVICE_PATH + "/instance", "localhost:8082");
    }
}
