package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.ConstantMap;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanyMapper;
import com.silita.dao.TbCompanySecurityCertMapper;
import com.silita.model.TbCompanyHighwayGrade;
import com.silita.model.TbCompanySecurityCert;
import com.silita.service.ICompanySecurityCertService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.ExcelUtils;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.stringUtils.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * tb_Company_security_cert serviceimpl
 */
@Service
public class CompanySecurityCertServiceImpl extends AbstractService implements ICompanySecurityCertService {

    @Autowired
    TbCompanySecurityCertMapper tbCompanySecurityCertMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    TbCompanyMapper companyMapper;
    @Autowired
    PropertiesUtils propertiesUtils;

    @Override
    public Map<String, Object> getCompanySecurity(TbCompanySecurityCert companySecurityCert) {
        Map<String, Object> resultMap = new HashMap<>();
        List<TbCompanySecurityCert> list = tbCompanySecurityCertMapper.queryCompanySecurityList(companySecurityCert);
        if (null != list && list.size() > 0) {
            for (TbCompanySecurityCert companySecurity : list) {
                if (null != companySecurity.getCertCityCode()) {
                    companySecurity.setCertCity(sysAreaMapper.queryAreaName(companySecurity.getCertCityCode()));
                }
                if (null != companySecurity.getCertProvCode()) {
                    companySecurity.setCertProv(sysAreaMapper.queryAreaName(companySecurity.getCertProvCode()));
                }
                if (Constant.SOURCE_PRO.equals(companySecurity.getCertOrigin())) {
                    resultMap.put("pro", companySecurity);
                    resultMap.put("lab", new TbCompanySecurityCert());
                    continue;
                }
                resultMap.put("lab", companySecurity);
                resultMap.put("pro", new TbCompanySecurityCert());
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> addCertNo(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String, Object> resultMap = new HashMap<>();
        //判断是否存在
        Integer count = tbCompanySecurityCertMapper.queryCertNoCount(companySecurityCert.getCertNo());
        if (count > 0) {
            resultMap.put("code", Constant.CODE_WARN_400);
            resultMap.put("msg", Constant.MSG_WARN_400);
            return resultMap;
        }
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if (null != cert) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(), null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public void delCompanySeu(String pkid) {
        tbCompanySecurityCertMapper.deleteCompanySecurity(pkid);
    }

    @Override
    public Map<String, Object> addSecurity(TbCompanySecurityCert companySecurityCert, String username) {
        Map<String, Object> resultMap = new HashMap<>();
        companySecurityCert.setCertOrigin(Constant.SOURCE_LAB);
        TbCompanySecurityCert cert = tbCompanySecurityCertMapper.queryCompanySecurityDetail(companySecurityCert);
        if (null != cert) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(cert.getPkid());
        }
        companySecurityCert.setPkid(DataHandlingUtil.getUUID());
        companySecurityCert.setCreateBy(username);
        companySecurityCert.setCreated(new Date());
        companySecurityCert.setExpired(MyDateUtils.strToDate(companySecurityCert.getExpiredStr(), null));
        tbCompanySecurityCertMapper.insertCompanySecurity(companySecurityCert);
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> listCompanySecurity(Map<String, Object> param) {
        TbCompanySecurityCert cert = new TbCompanySecurityCert();
        cert.setCertProvCode(MapUtils.getString(param, "certProvCode"));
        cert.setExpiredStr(MapUtils.getString(param, "expired"));
        cert.setCertNo(MapUtils.getString(param, "certNo"));
        cert.setCertCityCode(MapUtils.getString(param, "certCityCode"));
        cert.setCertLevel(MapUtils.getString(param, "certLevel"));
        cert.setCertResult(MapUtils.getString(param, "certResult"));
        cert.setComName(MapUtils.getString(param, "comName"));
        cert.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        cert.setPageSize(MapUtils.getInteger(param, "pageSize"));
        cert.setIssueDate(MapUtils.getString(param, "issueDate"));
        cert.setTabType(MapUtils.getString(param, "tabType"));
        Map<String, Object> result = new HashMap<>();
        Integer total = tbCompanySecurityCertMapper.queryCompanyCertCount(cert);
        if (total <= 0) {
            return null;
        }
        result.put("list", tbCompanySecurityCertMapper.queryCompanyCertList(cert));
        result.put("total", total);
        return super.handlePageCount(result, cert);
    }

    @Override
    public void delCompanySecurity(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanySecurityCertMapper.deleteCompanySecurity(pkid);
        }
    }

    @Override
    public Map<String, Object> batchExportCompanySecurity(Sheet sheet, String username, String fileName, String tabType) throws IOException {
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return null;
        }
        Cell cell;
        Row row;
        List<Map<String, Object>> excelList = new ArrayList<>();
        Map<String, Object> excelMap;
        StringBuffer sbf;
        String comId;
        String provCode;
        boolean isError = true;
        for (int i = 1; i <= rowCount; i++) {
            row = sheet.getRow(i);
            if (null == row) {
                continue;
            }
            excelMap = new HashMap<>();
            sbf = new StringBuffer();
            //企业名称
            cell = row.getCell(0);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    comId = companyMapper.queryComIdByName(cell.getStringCellValue());
                    if (comId == null) {
                        sbf.append("企业不存在");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("comId", comId);
                    }
                    excelMap.put("comName", cell.getStringCellValue());
                }
            }
            //安全生产许可证号
            cell = row.getCell(1);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("certNo", cell.getStringCellValue());
                }
            }
            //省
            cell = row.getCell(2);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    provCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                    if (null == provCode) {
                        sbf.append("，省份错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("provCode", provCode);
                    }
                    excelMap.put("prov", cell.getStringCellValue());
                }
            }
            //发布日期
            cell = row.getCell(3);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (!MyDateUtils.checkDate(cell.getStringCellValue())) {
                        sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                        if (isError) {
                            isError = false;
                        }
                    }
                }
                excelMap.put("issueDate", cell.getStringCellValue());
            }
            //有效期至
            cell = row.getCell(4);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (!MyDateUtils.checkDate(cell.getStringCellValue())) {
                        sbf.append("，有效期至格式不正确(yyyy-MM-dd)");
                        if (isError) {
                            isError = false;
                        }
                    }
                    excelMap.put("expired", cell.getStringCellValue());
                }
            }
            if (null != sbf && !"".equals(sbf.toString())) {
                excelMap.put("sdf", StringUtils.trimFirstAndLastChar(sbf.toString(), '，'));
            }
            excelMap.put("pkid", DataHandlingUtil.getUUID());
            excelMap.put("createdBy", username);
            excelList.add(excelMap);
        }

        Map<String, Object> resultMap = new HashMap<>();
        if (!isError) {
            String fileUrl = uploadExcel(excelList, fileName, tabType);
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", fileUrl);
            return resultMap;
        }

        List<TbCompanySecurityCert> list = doWeight(excelList);
        if (null != list && list.size() > 0) {
            tbCompanySecurityCertMapper.batchCompanyCert(list);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> batchExportCompanySafetyCert(Sheet sheet, String username, String fileName, String tabType) throws IOException {
        int rowCount = sheet.getLastRowNum();
        if (rowCount < 1) {
            return null;
        }
        Cell cell;
        Row row;
        List<Map<String, Object>> excelList = new ArrayList<>();
        Map<String, Object> excelMap;
        StringBuffer sbf;
        String comId;
        String provCode;
        String cityCode;
        boolean isError = true;
        for (int i = 1; i <= rowCount; i++) {
            row = sheet.getRow(i);
            if (null == row) {
                continue;
            }
            excelMap = new HashMap<>();
            sbf = new StringBuffer();
            //企业名称
            cell = row.getCell(0);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    comId = companyMapper.queryComIdByName(cell.getStringCellValue());
                    if (comId == null) {
                        sbf.append("企业不存在");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("comId", comId);
                    }
                    excelMap.put("comName", cell.getStringCellValue());
                }
            }
            //级别
            cell = row.getCell(1);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (null == ConstantMap.CERTLEVELMAP.get(cell.getStringCellValue())) {
                        sbf.append("，认证级别错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("certLevel", ConstantMap.CERTLEVELMAP.get(cell.getStringCellValue()));
                    }
                    excelMap.put("level", cell.getStringCellValue());
                }
            }
            //认证结果
            cell = row.getCell(2);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (null == ConstantMap.CERTRESULTMAP.get(cell.getStringCellValue())) {
                        sbf.append("，认证结果错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("certResult", ConstantMap.CERTRESULTMAP.get(cell.getStringCellValue()));
                    }
                    excelMap.put("result", cell.getStringCellValue());
                }
            }
            //省
            cell = row.getCell(3);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    provCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                    if (null == provCode) {
                        sbf.append("，省份错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("provCode", provCode);
                    }
                    excelMap.put("prov", cell.getStringCellValue());
                }
            }
            //市
            cell = row.getCell(4);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    cityCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                    if (null == cityCode) {
                        sbf.append("，省份错误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("cityCode", cityCode);
                    }
                    excelMap.put("city", cell.getStringCellValue());
                }
            }
            //发布日期
            cell = row.getCell(5);
            if (null != cell) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    String issueDate = null;
                    if (null != cell.getDateCellValue()) {
                        issueDate = MyDateUtils.excelTime(cell.getDateCellValue());
                        if (!MyDateUtils.checkDate(issueDate)) {
                            sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                            if (isError) {
                                isError = false;
                            }
                        }
                    }
                    excelMap.put("issueDate", issueDate);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                    if (isError) {
                        isError = false;
                    }
                    excelMap.put("issueDate", cell.getStringCellValue());
                }
            }
            //有效期至
            cell = row.getCell(6);
            if (null != cell) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    String expired = null;
                    if (null != cell.getDateCellValue()) {
                        expired = MyDateUtils.excelTime(cell.getDateCellValue());
                        if (!MyDateUtils.checkDate(expired)) {
                            sbf.append("，有效期至格式不正确(yyyy-MM-dd)");
                            if (isError) {
                                isError = false;
                            }
                        }
                    }
                    excelMap.put("expired", expired);
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    sbf.append("，有效期至格式不正确(yyyy-MM-dd)");
                    if (isError) {
                        isError = false;
                    }
                    excelMap.put("expired", cell.getStringCellValue());
                }
            }
            if (null != sbf && !"".equals(sbf.toString())) {
                excelMap.put("sdf", StringUtils.trimFirstAndLastChar(sbf.toString(), '，'));
            }
            excelMap.put("pkid", DataHandlingUtil.getUUID());
            excelMap.put("createdBy", username);
            excelList.add(excelMap);
        }

        Map<String, Object> resultMap = new HashMap<>();
        if (!isError) {
            String fileUrl = uploadExcel(excelList, fileName, tabType);
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", fileUrl);
            return resultMap;
        }

        List<TbCompanySecurityCert> list = doWeight(excelList);
        if (null != list && list.size() > 0) {
            tbCompanySecurityCertMapper.batchCompanyCert(list);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    private String uploadExcel(List<Map<String, Object>> excelList, String fileName, String tabType) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("安全认证");  // 创建第一个Sheet页;
        Row row = sheet.createRow(0); // 创建一个行
        row.setHeightInPoints(30); //设置这一行的高度
        ExcelUtils.createCell(wb, row, (short) 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "企业名称");
        if ("safety_cert".equals(tabType)) {
            ExcelUtils.createCell(wb, row, (short) 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "级别");
            ExcelUtils.createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "等级");
            ExcelUtils.createCell(wb, row, (short) 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "省");
            ExcelUtils.createCell(wb, row, (short) 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "市");
            ExcelUtils.createCell(wb, row, (short) 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "发布日期");
            ExcelUtils.createCell(wb, row, (short) 6, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "有效期至");
            ExcelUtils.createCell(wb, row, (short) 7, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "错误原因");
        }else {
            ExcelUtils.createCell(wb, row, (short) 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "安全生产许可证号");
            ExcelUtils.createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "省");
            ExcelUtils.createCell(wb, row, (short) 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "发布日期");
            ExcelUtils.createCell(wb, row, (short) 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "有效期至");
            ExcelUtils.createCell(wb, row, (short) 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "错误原因");
        }

        for (int i = 0; i < excelList.size(); i++) {
            row = sheet.createRow(i + 1); // 创建一个行
            row.setHeightInPoints(30); //设置这一行的高度
            if (null != excelList.get(i).get("comName")) {
                ExcelUtils.createCell(wb, row, 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("comName").toString());
            }
            if ("safety_cert".equals(tabType)) {
                if(null != excelList.get(i).get("level")){
                    ExcelUtils.createCell(wb, row, 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("level").toString());
                }
                if(null != excelList.get(i).get("result")){
                    ExcelUtils.createCell(wb, row, 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("result").toString());
                }
                if (null != excelList.get(i).get("prov")) {
                    ExcelUtils.createCell(wb, row, 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("prov").toString());
                }
                if(null != excelList.get(i).get("city")){
                    ExcelUtils.createCell(wb, row, 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("city").toString());
                }
                if (null != excelList.get(i).get("issueDate")) {
                    ExcelUtils.createCell(wb, row, 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("issueDate").toString());
                }
                if (null != excelList.get(i).get("expired")) {
                    ExcelUtils.createCell(wb, row, 6, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("expired").toString());
                }
                if (null != excelList.get(i).get("sdf")) {
                    ExcelUtils.createCell(wb, row, 7, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("sdf").toString());
                }
            }else{
                if (null != excelList.get(i).get("certNo")) {
                    ExcelUtils.createCell(wb, row, 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("certNo").toString());
                }
                if (null != excelList.get(i).get("prov")) {
                    ExcelUtils.createCell(wb, row, 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("prov").toString());
                }
                if (null != excelList.get(i).get("issueDate")) {
                    ExcelUtils.createCell(wb, row, 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("issueDate").toString());
                }
                if (null != excelList.get(i).get("expired")) {
                    ExcelUtils.createCell(wb, row, 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("expired").toString());
                }
                if (null != excelList.get(i).get("sdf")) {
                    ExcelUtils.createCell(wb, row, 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("sdf").toString());
                }
            }
        }
        String fileUrl = propertiesUtils.getFilePath() + "//error_excel//" + fileName;
        FileOutputStream fileOut = new FileOutputStream(fileUrl);
        wb.write(fileOut);
        fileOut.close();
        if ("pre".equals(propertiesUtils.getServer()) || "pro".equals(propertiesUtils.getServer())) {
            String newFileUrl = propertiesUtils.getLocalhostServer() + "/error_excel/" + fileName;
            return newFileUrl;
        }
        return fileUrl;
    }

    private List<TbCompanySecurityCert> doWeight(List<Map<String, Object>> excelList) {
        List<TbCompanySecurityCert> resultList = new ArrayList<>();
        TbCompanySecurityCert cert;
        for (Map<String, Object> map : excelList) {
            Integer count = tbCompanySecurityCertMapper.queryCertCount(map);
            if (count <= 0) {
                cert = new TbCompanySecurityCert();
                cert.setPkid(MapUtils.getString(map, "pkid"));
                cert.setComId(MapUtils.getString(map, "comId"));
                cert.setCertNo(MapUtils.getString(map, "certNo"));
                cert.setCertLevel(MapUtils.getString(map, "certLevel"));
                cert.setCertResult(MapUtils.getString(map, "certResult"));
                if (null != MapUtils.getString(map, "expired")) {
                    cert.setExpired(MyDateUtils.strToDate(MapUtils.getString(map, "expired"), "yyyy-MM-dd"));
                }
                cert.setCertProvCode(MapUtils.getString(map, "provCode"));
                cert.setCertCityCode(MapUtils.getString(map, "cityCode"));
                cert.setIssueDate(MapUtils.getString(map, "issueDate"));
                cert.setCreateBy(MapUtils.getString(map, "createdBy"));
                resultList.add(cert);
            }
        }
        return resultList;
    }
}
