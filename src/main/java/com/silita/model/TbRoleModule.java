package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
public class TbRoleModule {
    /**
     * 角色id
     */
    private Integer rid;

    /**
     * 权限id
     */
    private Integer id;

}