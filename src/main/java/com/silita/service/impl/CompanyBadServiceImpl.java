package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.ConstantMap;
import com.silita.dao.TbCompanyBadMapper;
import com.silita.dao.TbCompanyMapper;
import com.silita.model.TbCompanyBad;
import com.silita.model.TbCompanySecurityCert;
import com.silita.service.ICompanyBadService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.ExcelUtils;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.stringUtils.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_company_bad ServiceImpl
 */
@Service
public class CompanyBadServiceImpl extends AbstractService implements ICompanyBadService {

    @Autowired
    TbCompanyBadMapper tbCompanyBadMapper;
    @Autowired
    TbCompanyMapper companyMapper;
    @Autowired
    PropertiesUtils propertiesUtils;

    @Override
    public Map<String, Object> getCompanyBadList(Map<String, Object> param) {
        TbCompanyBad companyBad = mapToClass(param);
        Integer total = tbCompanyBadMapper.queryCompanyBadCount(companyBad);
        if (total <= 0) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", tbCompanyBadMapper.queryCompanyBadList(companyBad));
        result.put("total", total);
        return super.handlePageCount(result, companyBad);
    }

    @Override
    public void batchDelCompanyBad(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanyBadMapper.deleteCompanyBad(pkid);
        }
    }

    @Override
    public void checkAllDelCompanyBad(Map<String, Object> param) {
        TbCompanyBad bad = mapToClass(param);
        List<Map<String, Object>> list = tbCompanyBadMapper.queryCompanyBadList(bad);
        if (null != list && list.size() > 0) {
            tbCompanyBadMapper.batchDeleteCompanyBad(list);
        }
    }

    @Override
    public Map<String, Object> batchImportCompanyBad(Sheet sheet, String username, String fileName) throws IOException {
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
            //项目
            cell = row.getCell(1);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("proName", cell.getStringCellValue());
                }
            }

            //不良行为内容
            cell = row.getCell(2);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("badInfo", cell.getStringCellValue());
                }
            }

            //性质
            cell = row.getCell(3);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (null == ConstantMap.PROPERTYMAP.get(cell.getStringCellValue().trim())) {
                        sbf.append("，性质输入有误");
                        if (isError) {
                            isError = false;
                        }
                    } else {
                        excelMap.put("property", ConstantMap.PROPERTYMAP.get(cell.getStringCellValue()));
                    }
                    excelMap.put("propertyName", cell.getStringCellValue());
                }
            }
            //发布单位
            cell = row.getCell(4);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    excelMap.put("issueOrg", cell.getStringCellValue());
                }
            }
            //发布日期
            cell = row.getCell(5);
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
            cell = row.getCell(6);
            if (null != cell) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                    if (!MyDateUtils.checkDate(cell.getStringCellValue())) {
                        if (!MyDateUtils.checkDate(cell.getStringCellValue())) {
                            sbf.append("，有效期至格式不正确(yyyy-MM-dd)");
                            if (isError) {
                                isError = false;
                            }
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
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", uploadExcel(excelList, "import", fileName));
            return resultMap;
        }
        List<TbCompanyBad> list = doWeight(excelList);
        if (null != list && list.size() > 0) {
            tbCompanyBadMapper.batchInsertCompanyBad(list);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public String batchExportCompanyBad(Map<String, Object> param) throws IOException {
        TbCompanyBad bad = mapToClass(param);
        List<Map<String, Object>> list = tbCompanyBadMapper.queryCompanyBadList(bad);
        if (null != list && list.size() > 0) {
            return this.uploadExcel(list, "exprort", "企业不良信息_" + DataHandlingUtil.getUUID() + ".xlsx");
        }
        return null;
    }

    private String uploadExcel(List<Map<String, Object>> excelList, String type, String fileName) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("不良记录");  // 创建第一个Sheet页;
        Row row = sheet.createRow(0); // 创建一个行
        row.setHeightInPoints(30); //设置这一行的高度
        ExcelUtils.createCell(wb, row, (short) 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "企业名称");
        ExcelUtils.createCell(wb, row, (short) 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "项目");
        ExcelUtils.createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "不良行为内容");
        ExcelUtils.createCell(wb, row, (short) 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "发布单位");
        ExcelUtils.createCell(wb, row, (short) 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "性质");
        ExcelUtils.createCell(wb, row, (short) 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "发布日期");
        ExcelUtils.createCell(wb, row, (short) 6, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "有效期至");
        if ("import".equals(type)) {
            ExcelUtils.createCell(wb, row, (short) 7, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "错误原因");
        }
        for (int i = 0; i < excelList.size(); i++) {
            row = sheet.createRow(i + 1); // 创建一个行
            row.setHeightInPoints(30); //设置这一行的高度
            if (null != excelList.get(i).get("comName")) {
                ExcelUtils.createCell(wb, row, 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("comName").toString());
            }
            if (null != excelList.get(i).get("proName")) {
                ExcelUtils.createCell(wb, row, 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("proName").toString());
            }
            if (null != excelList.get(i).get("badInfo")) {
                ExcelUtils.createCell(wb, row, 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("badInfo").toString());
            }
            if (null != excelList.get(i).get("issueOrg")) {
                ExcelUtils.createCell(wb, row, 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("issueOrg").toString());
            }
            if (null != excelList.get(i).get("propertyName")) {
                ExcelUtils.createCell(wb, row, 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("propertyName").toString());
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
        }
        String fileUrl = propertiesUtils.getFilePath() + "//error_excel//" + fileName;
        FileOutputStream fileOut = new FileOutputStream(fileUrl);
        wb.write(fileOut);
        fileOut.close();
        if ("pre".equals(propertiesUtils.getServer()) || "pro".equals(propertiesUtils.getServer())) {
            String proFileUrl = propertiesUtils.getLocalhostServer() + "/error_excel/" + fileName;
        }
        return fileUrl;
    }

    private List<TbCompanyBad> doWeight(List<Map<String, Object>> excelList) {
        List<TbCompanyBad> resultList = new ArrayList<>();
        TbCompanyBad bad;
        for (Map<String, Object> map : excelList) {
            Integer count = tbCompanyBadMapper.queryBadCount(map);
            if (count <= 0) {
                bad = new TbCompanyBad();
                bad.setPkid(MapUtils.getString(map, "pkid"));
                bad.setComId(MapUtils.getString(map, "comId"));
                bad.setProName(MapUtils.getString(map, "proName"));
                bad.setBadInfo(MapUtils.getString(map, "badInfo"));
                bad.setProperty(MapUtils.getString(map, "property"));
                bad.setIssueOrg(MapUtils.getString(map, "issueOrg"));
                bad.setExpired(MapUtils.getString(map, "expired"));
                bad.setIssueDate(MapUtils.getString(map, "issueDate"));
                bad.setCreateBy(MapUtils.getString(map, "createdBy"));
                resultList.add(bad);
            }
        }
        return resultList;
    }

    private TbCompanyBad mapToClass(Map<String, Object> param) {
        TbCompanyBad companyBad = new TbCompanyBad();
        if (null != param.get("currentPage")) {
            companyBad.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
            companyBad.setPageSize(MapUtils.getInteger(param, "pageSize"));
        }
        companyBad.setComName(MapUtils.getString(param, "comName"));
        companyBad.setProName(MapUtils.getString(param, "proName"));
        companyBad.setBadInfo(MapUtils.getString(param, "badInfo"));
        companyBad.setIssueOrg(MapUtils.getString(param, "issueOrg"));
        companyBad.setProperty(MapUtils.getString(param, "property"));
        companyBad.setIssueDate(MapUtils.getString(param, "issueDate"));
        companyBad.setExpired(MapUtils.getString(param, "expired"));
        return companyBad;
    }
}
