package com.silita.hbase;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * hbase配置类
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2019-02-26 14:57
 */

@Configuration
public class HBaseConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

/*
    private String hbaseZookeeperQuorum="192.168.2.101";

    private String hbaseZookeeperclientPort="2181";

    private String hbaseMaster="192.168.2.101 60000";

    private String hbaseRootdir="hdfs://192.168.2.101:9000/hbase";*/



   /* private String hbaseZookeeperQuorum="192.168.88.222";

    private String hbaseZookeeperclientPort="2181";

    private String hbaseMaster="DESKTOP-3R7HQ4C:60000";

    private String hbaseRootdir="hdfs://DESKTOP-3R7HQ4C:8020/hbase";*/

   @Value("${hbase.config.hbase.zookeeper.quorum}")
    private String hbaseZookeeperQuorum;
    @Value("${hbase.config.hbase.zookeeper.property.clientPort}")
    private String hbaseZookeeperclientPort;
    @Value("${hbase.config.hbase.master}")
    private String hbaseMaster;
    @Value("${hbase.config.hbase.rootdir}")
    private String hbaseRootdir;

    @Bean
    public Connection hBaseConnection() {
        Connection connection = null;
        try {
            connection = ConnectionFactory.createConnection(configuration());
            logger.info("#####连接hbase成功#####");
        } catch (IOException e) {
            e.printStackTrace();
//            logger.error("连接hbase失败!!!");
        }
        return connection;
    }

    @Bean
    public org.apache.hadoop.conf.Configuration configuration() {
//        System.getProperties().put("hadoop.home.dir", hadoopHomeDir);
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", hbaseZookeeperQuorum);
        configuration.set("hbase.zookeeper.property.clientPort", hbaseZookeeperclientPort);
        configuration.set("hbase.master", hbaseMaster);
        configuration.set("hbase.rootdir", hbaseRootdir);
        return configuration;
    }
}
