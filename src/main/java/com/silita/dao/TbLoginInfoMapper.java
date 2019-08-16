package com.silita.dao;

import com.silita.model.TbLoginInfo;
import com.silita.utils.MyMapper;

public interface TbLoginInfoMapper extends MyMapper<TbLoginInfo> {

    /**
     *
     * 统计用户登录天数
      * @param phoneNo
     * @return
     */
    Integer queryLoginCount(String phoneNo);
}