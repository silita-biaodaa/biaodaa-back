package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TbRole implements Serializable {

	/**
	 * 主键
	 */
	private Integer rid;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 更新时间
	 */
	private Date updated;

	private List<TbPermission> permissions;


}
