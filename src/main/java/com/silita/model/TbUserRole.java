package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class TbUserRole {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 管理员id
     */
    private Integer uid;

    /**
     * 角色id
     */
    private Integer rid;

}