package com.silita.dao;

import java.util.Set;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface IPermissionMapper {

    /**
     *
     * @param userName
     * @return
     */
    public Set<String> listPermissionByUserName(String userName);
}
