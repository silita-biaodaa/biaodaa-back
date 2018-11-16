package com.silita.service;

import com.silita.model.TbCompanyHighwayGrade;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * tb_company_highway_grade service
 */
public interface ICompanyHighwayGradeService {

    /**
     * 获取信用等级
     *
     * @param grade
     * @return
     */
    List<Map<String, Object>> getCompanyHighwayGradeList(TbCompanyHighwayGrade grade);

    /**
     * 获取信用等级地区
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> getHighwayProv(Map<String, Object> param);

    /**
     * 添加信用等级
     *
     * @param grade
     * @param username
     * @return
     */
    Map<String, Object> addHighway(TbCompanyHighwayGrade grade, String username);

    /**
     * 获取企业公路信用等级
     *
     * @param param
     * @return
     */
    Map<String, Object> getCompanyHighwayGradeForCompanyList(Map<String, Object> param);

    /**
     * 删除
     *
     * @param param
     */
    void deleteCompanyHigwagGrade(Map<String, Object> param);

    /**
     * 删除
     *
     * @param param
     */
    void checkAllDeleteCompanyHigwagGrade(Map<String, Object> param);

    /**
     * 批量导入企业信息
     *
     * @param sheet    excel页
     * @param username 用户名
     * @param fileName 文件名
     * @return
     */
    Map<String, Object> batchImportCompanyHighwayGrade(Sheet sheet, String username, String fileName) throws IOException;

    /**
     * 批量导出excel
     * @param param
     * @return
     * @throws IOException
     */
    String batchExportCompanyHighwayGrade(Map<String,Object> param) throws IOException;
}
