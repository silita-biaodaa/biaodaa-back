package com.silita.model;


import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class TbPermission extends Pagination {

	/**
	 * 主键
	 */
	private Integer pid;

	/**
	 * 权限名称
	 */
	private String permissionName;

	/**
	 * 描述
	 */
	private  String desc;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 更新时间
	 */
	private Date updated;
}
