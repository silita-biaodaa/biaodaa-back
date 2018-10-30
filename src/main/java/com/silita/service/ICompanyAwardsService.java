package com.silita.service;

import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.Map;

/**
 * tb_company_awards Service
 */
public interface ICompanyAwardsService {

    /**
     * 获取获奖列表
     * @param param
     * @return
     */
    Map<String,Object> getCompanyAwardsList(Map<String,Object> param);

    /**
     * 批量删除
     * @param param
     */
    void batchDelCompanyAwards(Map<String,Object> param);

    /**
     * 批量导入企业信息
     * @param sheet excel页
     * @param username 用户名
     * @param fileName 文件名
     * @return
     */
    Map<String,Object> batchExportCompanyAwards(Sheet sheet,String username,String fileName) throws IOException;
}
