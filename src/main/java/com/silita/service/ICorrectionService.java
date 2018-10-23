package com.silita.service;

import com.silita.model.AllZh;
import com.silita.model.Snatchurl;
import com.silita.model.ZhaobiaoDetailOthers;
import com.silita.model.ZhongbiaoDetailOthers;

import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-18 14:26
 */
public interface ICorrectionService {

    /**
     * 根据资质名称筛选资质别名
     * @return
     */
    List<AllZh> ListAllZhByName(AllZh allZh);

    /**
     * 根据查询条件获取招、中标纠错列表
     * @return
     */
    Map<String, Object> listSnatchurl(Snatchurl snatchurl);

    /**
     * 根据公告pkid更新公告显示状态（逻辑删除）
     * @param snatchurl
     * @return
     */
    Integer updateSnatchurlIsShowById(Snatchurl snatchurl);

    /**
     * 根据公告pkid获取招标公告标段信息
     * @param zhaobiaoDetailOthers
     * @return
     */
    List<ZhaobiaoDetailOthers> listZhaobiaoDetailBySnatchUrlId(ZhaobiaoDetailOthers zhaobiaoDetailOthers);

    /**
     * 根据标段pkid更新招标公告标段信息
     * @param zhaobiaoDetailOthers
     * @return
     */
    Integer updateZhaobiaoDetailById(ZhaobiaoDetailOthers zhaobiaoDetailOthers);

    /**
     * 根据企业名称或者拼音获取ES中企业信息
     * @return
     */
    public List<Map<String, Object>> listCompanyByNameOrPinYin(String queryKey);

    /**
     * 根据公告pkid获取中标公告标段信息
     * @param zhongbiaoDetailOthers
     * @return
     */
    List<ZhongbiaoDetailOthers> listZhongbiaoDetailBySnatchUrlId(ZhongbiaoDetailOthers zhongbiaoDetailOthers);

    /**
     * 根据标段pkid更新中标公告标段信息
     * @param zhongbiaoDetailOthers
     * @return
     */
    Integer updateZhongbiaoDetailById(ZhongbiaoDetailOthers zhongbiaoDetailOthers);
}
