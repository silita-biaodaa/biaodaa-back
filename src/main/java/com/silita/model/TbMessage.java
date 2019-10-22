package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;
@Getter
@Setter
public class TbMessage {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 回复表主键id
     */
    private String replyId;

    /**
     * 接收人user_id
     */
    private String userId;

    /**
     * 标题
     */
    private String msgTitle;

    /**
     * 消息类型(reply:回复评论消息)
     */
    private String msgType;

    /**
     * 消息时间
     */
    private Date pushd;

    /**
     * 是否已读(0:否，1:是)
     */
    private Integer isRead;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 消息内容
     */
    private String msgContent;


}