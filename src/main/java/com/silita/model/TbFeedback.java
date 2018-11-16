package com.silita.model;

import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.*;

@Setter
@Getter
public class TbFeedback extends Pagination {
    /**
     * 主键
     */
    private String pkid;

    /**
     * 反馈用户名(sys_user_info.pkid)
     */
    private String fbUserId;

    /**
     * 意见类别
     */
    private Integer type;

    /**
     * 反馈内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 创建人
     */
    private String createBy;
}