package com.silita.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**
 * 湖南公路
 *
 * @author Antoneo
 * @create 2020-09-09 14:54
 **/
@Getter
@Setter
@Document(collection = "hunan_highway_achieve")
public class HunanHighway {
    @Id
    private String id;

    private String projectName;

    private String contractName;

    private String contractBeginNo;

    private String contractFinishNo;

    private String location;

    private String mainWorks;

    //审核状态: 0未操作，1操作中，2已操作
    private  int  isOpt;

    //人工解析里程数
    private String mileageMan;
}
