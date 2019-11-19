package com.bo.zou.zookeeper_client.client;

import com.bo.zou.zookeeper_client.configuration.ZKClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@Order(value = 1)
public class Client implements ApplicationRunner {

    private static final String SERVICE_PATH = "/service/provider";

    private static final String CONFIG_PATH = "/config";

    private List<String> urls = new ArrayList<>();

    @Autowired
    private ZKClient zkClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("consumer started");
        configuration();
        serviceDiscovery();
    }

    private void serviceDiscovery() throws Exception {
        final PathChildrenCache childrenCache = new PathChildrenCache(zkClient.getClient(), SERVICE_PATH, false);
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("=================provider changed=======================");
                urls = zkClient.getChildren(SERVICE_PATH);
                System.out.println(urls.toString());
            }
        });
        childrenCache.start();
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

    public String getUrl() throws Exception {
        if (urls.isEmpty()) {
            return null;
        }
        int index = new Random().nextInt(urls.size());
        return zkClient.getData(SERVICE_PATH + "/" + urls.get(index));
    }
}
