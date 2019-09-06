package com.silita.dao;

import com.silita.model.TbRole;

import javax.management.relation.Role;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface IRoleMapper {

    /**
     *
     * @param userName
     * @return
     */
    public Set<String> listRoleByUserName(String userName);

    /**
     * 添加角色
     * @param param
     */
    void addRole(Map<String,Object> param);

    /**
     * 修改角色
     * @param param
     */
    void updateRole(Map<String,Object> param);

    /**
     * 获取最大角色id
     * @return
     */
    Integer queryRoleMaxRid();

    /**
     * 获取角色名称
     * @param param
     * @return
     */
    String queryDesc(Map<String,Object> param);

    /**
     * 根据id查询角色名称
     * @param param
     * @return
     */
    String queryDescRid(Map<String,Object> param);

    /**
     * 获取所有角色
     * @return
     */
    List<Map<String,Object>> queryRoleAll();

    /**
     * 查询角色列表
     * @param role
     * @return
     */
    List<Map<String,Object>> queryRoleList(TbRole role);

    /**
     * 查询角色列表统计
     * @param role
     * @return
     */
    Integer queryRoleListCount(TbRole role);




}
