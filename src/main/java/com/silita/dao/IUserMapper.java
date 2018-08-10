package com.silita.dao;


import com.silita.model.TbUser;

/**
 * Created by Administrator on 2017/10/11.
 */
public interface IUserMapper {

    /**
     *
     * @param userName
     * @return
     */
    public TbUser getUserByUserName(String userName);

    /**
     *
     * @param userName
     * @return
     */
    public TbUser getRolesAndPermissionsByUserName(String userName);

}
