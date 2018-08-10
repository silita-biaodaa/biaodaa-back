package com.silita.dao;

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
}
