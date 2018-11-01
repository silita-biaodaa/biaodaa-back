package com.silita.service;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.Map;

/**
 * tb_company_bad Service
 */
public interface ICompanyBadService {

    /**
     * 获取企业不良行为列表
     *
     * @param param
     * @return
     */
    Map<String, Object> getCompanyBadList(Map<String, Object> param);

    /**
     * 批量删除
     *
     * @param param
     */
    void batchDelCompanyBad(Map<String, Object> param);

    /**
     * 批量导入
     *
     * @param sheet
     * @param username
     * @param fileName
     * @return
     * @throws IOException
     */
    Map<String, Object> batchExportCompanyHighwayGrade(Sheet sheet, String username, String fileName) throws IOException;
}
