package com.silita.dao;

import com.silita.model.TbCompanyQualification;
import com.silita.utils.MyMapper;

import java.util.List;

public interface TbCompanyQualificationMapper extends MyMapper<TbCompanyQualification> {

    /**
     * 查询企业资质
     * @param tbCompanyQualification
     * @return
     */
    List<TbCompanyQualification> queryCompanyQual(TbCompanyQualification tbCompanyQualification);

    /**
     * 查询资质是否存在
     * @param tbCompanyQualification
     * @return
     */
    Integer queryCompanyQualCount(TbCompanyQualification tbCompanyQualification);

    /**
     * 添加资质
     * @param tbCompanyQualification
     * @return
     */
    Integer insertCompanyQual(TbCompanyQualification tbCompanyQualification);

    /**
     * 修改资质
     * @param tbCompanyQualification
     * @return
     */
    Integer updateCompanyQual(TbCompanyQualification tbCompanyQualification);

    /**
     * 删除资质
     * @param pkid
     * @return
     */
    Integer deleteCompanyQual(String pkid);
}