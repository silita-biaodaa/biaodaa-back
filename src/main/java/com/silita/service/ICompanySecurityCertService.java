package com.silita.service;

import com.silita.model.TbCompanySecurityCert;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.util.Map;

/**
 * tb_Company_security_cert service
 */
public interface ICompanySecurityCertService {

    /**
     * 获取安许证号
     *
     * @param companySecurityCert
     * @return
     */
    Map<String, Object> getCompanySecurity(TbCompanySecurityCert companySecurityCert);

    /**
     * 添加安许证号
     *
     * @param companySecurityCert
     * @param username
     */
    Map<String, Object> addCertNo(TbCompanySecurityCert companySecurityCert, String username);

    /**
     * 删除
     *
     * @param pkid
     */
    void delCompanySeu(String pkid);

    /**
     * 添加安全认证
     *
     * @param companySecurityCert
     * @param username
     * @return
     */
    Map<String, Object> addSecurity(TbCompanySecurityCert companySecurityCert, String username);

    /**
     * 获取企业安全认证列表
     *
     * @param param
     * @return
     */
    Map<String, Object> listCompanySecurity(Map<String, Object> param);

    /**
     * 批量删除
     *
     * @param param
     */
    void delCompanySecurity(Map<String, Object> param);

    /**
     * 全选删除
     *
     * @param param
     */
    void checkAllDelCompanySecurity(Map<String, Object> param);

    /**
     * 安全生产许可证上传
     *
     * @param sheet
     * @param username
     * @param fileName
     * @return
     * @throws IOException
     */
    Map<String, Object> batchImportCompanySecurity(Sheet sheet, String username, String fileName, String tabType) throws IOException;

    /**
     * 安全认证
     *
     * @param sheet
     * @param username
     * @param fileName
     * @return
     * @throws IOException
     */
    Map<String, Object> batchImportCompanySafetyCert(Sheet sheet, String username, String fileName, String tabType) throws IOException;

    /**
     * 批量导出
     * @param param
     * @return
     * @throws IOException
     */
    String batchExportCompanySecu(Map<String,Object> param) throws IOException;
}
