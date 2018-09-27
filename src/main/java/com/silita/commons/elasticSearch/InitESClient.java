package com.silita.commons.elasticSearch;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-27 11:09
 * 获取ES客户端
 */
@Component("initESClient")
public class InitESClient {

    public static String ip;
    public static String clusterName;
    public static int port;

    private static TransportClient client;


    private InitESClient() {
    }

    public static TransportClient getInit() {
        try {
            if (null == client) {
                synchronized (InitESClient.class) {
                    Settings settings = Settings.builder()
                            .put("cluster.name", clusterName)
                            .put("client.transport.sniff", true)
                            .build();
                    client = new PreBuiltTransportClient(settings)
                            .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
                    System.out.println("elasticSearchClientInit");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return client;
    }
}
