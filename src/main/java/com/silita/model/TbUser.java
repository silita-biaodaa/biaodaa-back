package com.silita.model;


import com.silita.utils.split.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TbUser extends Pagination implements Serializable{
	/**
	 * 主键
	 */
	private Integer uid ;

	/**
	 * 账号
	 */
	private String userName ;

	/**
	 * 密码
	 */
	private String password ;

	/**
	 * 真实名称
	 */
	private String realName;
	/**
	 * 用户手机
	 */
	private String phone;

	/**
	 * 超级管理员标识，1超级管理员
	 */
	private Integer sflag;

	/**
	 * 账号锁标识，0未锁定，1锁定
	 */
	private Integer lock;

	/**
	 * 创建时间
	 */
	private Date created;

	/**
	 * 更新时间
	 */
	private Date updated;

	/**
	 *
	 */
	private List<TbRole> roles;
}
