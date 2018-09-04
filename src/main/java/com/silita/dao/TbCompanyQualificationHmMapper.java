package com.silita.dao;

import com.silita.model.TbCompanyQualificationHm;
import com.silita.utils.MyMapper;

import java.util.List;
import java.util.Map;

public interface TbCompanyQualificationHmMapper extends MyMapper<TbCompanyQualificationHm> {

    /**
     * 查询企业人工添加资质
     * @param tbCompanyQualificationHm
     * @return
     */
    List<TbCompanyQualificationHm> queryCompanyQualHm(TbCompanyQualificationHm tbCompanyQualificationHm);

    /**
     * 查询企业资质是否存在
     * @param param
     * @return
     */
    Integer queryCompanyQualCountByQual(Map<String,Object> param);

    /**
     * 添加
     * @param qualificationHm
     * @return
     */
    Integer insertCompanyQual(TbCompanyQualificationHm qualificationHm);

    /**
     * 删除
     * @param pkid
     * @return
     */
    Integer deleteCompanyQual(String pkid);
}