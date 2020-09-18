package com.silita.dao;


import com.silita.model.TbUser;
import com.silita.model.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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


    /**
     *
     * @param uid
     * @return
     */
    public String getRealName(@Param("uid")String uid);

    List<UserVo> getParseUser();

}
