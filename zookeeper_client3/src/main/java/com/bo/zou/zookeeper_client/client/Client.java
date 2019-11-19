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

    private static final String CONFIG_PATH = "/config";

    private static final String SERVICE_PATH = "/service/provider";

    @Autowired
    private ZKClient zkClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("provider started");
        configuration();
        zkClient.createNode(SERVICE_PATH + "/instance", "localhost:8083");
    }

    private void configuration() throws Exception {
        final NodeCache nodeCache = new NodeCache(zkClient.getClient(), CONFIG_PATH, false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("=================config updated=====================");
                System.out.println(zkClient.getData(CONFIG_PATH));
                System.out.println("=================output finished=====================");
            }
        });
        nodeCache.start();
    }
}
