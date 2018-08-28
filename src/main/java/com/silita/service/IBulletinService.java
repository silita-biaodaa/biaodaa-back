package com.silita.service;

import com.silita.model.DicCommon;

import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-27 16:52
 * 公告编辑service
 */
public interface IBulletinService {

    /**
     * 获取公告编辑固定数据
     * @return
     */
    Map<String, String> listFixedEditData();

    /**
     * 根据type获取省份评标办法
     * @param dicCommon
     * @return
     */
    List<String> listDicCommonNameByType(DicCommon dicCommon);
}
