package com.silita.service.impl;


import com.silita.common.Constant;
import com.silita.common.ConstantMap;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanyAwardsMapper;
import com.silita.dao.TbCompanyMapper;
import com.silita.model.TbCompanyAwards;
import com.silita.service.ICompanyAwardsService;
import com.silita.service.abs.AbstractService;
import com.silita.utils.DataHandlingUtil;
import com.silita.utils.ExcelUtils;
import com.silita.utils.PropertiesUtils;
import com.silita.utils.dateUtils.MyDateUtils;
import com.silita.utils.stringUtils.StringUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tb_company_awards ServiceImpl
 */
@Service
public class CompanyAwardsServiceImpl extends AbstractService implements ICompanyAwardsService {

    @Autowired
    TbCompanyAwardsMapper tbCompanyAwardsMapper;
    @Autowired
    TbCompanyMapper tbCompanyMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    PropertiesUtils propertiesUtils;

    @Override
    public Map<String, Object> getCompanyAwardsList(Map<String, Object> param) {
        TbCompanyAwards companyAwards = new TbCompanyAwards();
        companyAwards.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        companyAwards.setPageSize(MapUtils.getInteger(param, "pageSize"));
        companyAwards.setComName(MapUtils.getString(param, "comName"));
        companyAwards.setLevel(MapUtils.getString(param, "level"));
        companyAwards.setProTypeCode(MapUtils.getString(param, "proTypeCode"));
        companyAwards.setCityCode(MapUtils.getString(param, "cityCode"));
        companyAwards.setProvCode(MapUtils.getString(param, "provCode"));
        companyAwards.setAwdName(MapUtils.getString(param, "awdName"));
        companyAwards.setYear(MapUtils.getString(param, "year"));
        companyAwards.setProTypeName(MapUtils.getString(param, "proTypeName"));
        companyAwards.setProName(MapUtils.getString(param, "proName"));
        Integer total = tbCompanyAwardsMapper.queryCompanyAwardsCount(companyAwards);
        if (total <= 0) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("list", tbCompanyAwardsMapper.queryCompanyAwardsList(companyAwards));
        result.put("total", total);
        return super.handlePageCount(result, companyAwards);
    }

    @Override
    public void batchDelCompanyAwards(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanyAwardsMapper.deleteCompanyAwards(pkid);
        }
    }

    @Override
    public Map<String, Object> batchExportCompanyAwards(Sheet sheet, String username, String fileName) throws IOException {
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
        String level;
        String provCode;
        String cityCode;
        String issueDate = null;
        boolean isError = true;
        for (int i = 1; i <= rowCount; i++) {
            row = sheet.getRow(i);
            excelMap = new HashMap<>();
            sbf = new StringBuffer();
            //企业名称
            cell = row.getCell(0);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                comId = tbCompanyMapper.queryComIdByName(cell.getStringCellValue());
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
            //奖项名称
            cell = row.getCell(1);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                level = ConstantMap.AWARDSMAP.get(cell.getStringCellValue().trim());
                if (level == null) {
                    sbf.append("，奖项错误");
                    if (isError) {
                        isError = false;
                    }
                } else {
                    excelMap.put("level", level);
                }
                excelMap.put("levelName", cell.getStringCellValue());
            }
            //省
            cell = row.getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
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
            //市
            cell = row.getCell(3);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                cityCode = sysAreaMapper.queryAreaCode(cell.getStringCellValue());
                if (null == cityCode) {
                    sbf.append("，市错误");
                    if (isError) {
                        isError = false;
                    }
                } else {
                    excelMap.put("cityCode", cityCode);
                }
                excelMap.put("city", cell.getStringCellValue());
            }
            //奖项名称
            cell = row.getCell(4);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                excelMap.put("awdName", cell.getStringCellValue());
            }
            //年度
            cell = row.getCell(5);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                if (!StringUtils.isNumeric(cell.getStringCellValue())) {
                    sbf.append("，获奖年度格式错误");
                    if (isError) {
                        isError = false;
                    }
                }
                excelMap.put("year", cell.getStringCellValue());
            }
            //项目名称
            cell = row.getCell(6);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                excelMap.put("proName", cell.getStringCellValue());
            }
            //项目类型
            cell = row.getCell(7);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            if (null != cell.getStringCellValue()) {
                excelMap.put("proTypeName", cell.getStringCellValue());
            }
            //发布时间
            cell = row.getCell(8);
            if (null != cell) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    issueDate = MyDateUtils.excelTime(cell.getDateCellValue());
                    if (!MyDateUtils.checkDate(issueDate)) {
                        sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                        if (isError) {
                            isError = false;
                        }
                    }
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                        issueDate = cell.getStringCellValue();
                        sbf.append("，发布日期格式不正确(yyyy-MM-dd)");
                        if (isError) {
                            isError = false;
                        }
                    }
                }
                excelMap.put("issueDate", issueDate);
            }
            if (null != sbf && !"".equals(sbf.toString())) {
                excelMap.put("sdf", StringUtils.trimFirstAndLastChar(sbf.toString(), '，'));
            }
            excelMap.put("pkid", DataHandlingUtil.getUUID());
            excelMap.put("createBy", username);
            excelList.add(excelMap);
        }
        Map<String, Object> resultMap = new HashMap<>();
        if (!isError) {
            String fileUrl = uploadExcel(excelList, fileName);
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", fileUrl);
            return resultMap;
        }
        //去重
        List<Map<String,Object>> list = doWeight(excelList);
        if(null != list && list.size() > 0){
            tbCompanyAwardsMapper.batchInsertCompanyAwrds(doWeight(list));
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    private String uploadExcel(List<Map<String, Object>> excelList, String fileName) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("获奖信息");  // 创建第一个Sheet页;
        Row row = sheet.createRow(0); // 创建一个行
        row.setHeightInPoints(30); //设置这一行的高度
        ExcelUtils.createCell(wb, row, (short) 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "企业名称"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "奖项级别"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "省"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "市"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "奖项名称"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "获奖年度"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 6, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "项目名称"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 7, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "项目类型"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 8, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "发文日期"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 9, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "错误原因"); //要充满屏幕又要中间
        for (int i = 0; i < excelList.size(); i++) {
            row = sheet.createRow(i + 1); // 创建一个行
            row.setHeightInPoints(30); //设置这一行的高度
            if (null != excelList.get(i).get("comName")) {
                ExcelUtils.createCell(wb, row, 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("comName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("levelName")) {
                ExcelUtils.createCell(wb, row, 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("levelName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("prov")) {
                ExcelUtils.createCell(wb, row, 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("prov").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("city")) {
                ExcelUtils.createCell(wb, row, 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("city").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("awdName")) {
                ExcelUtils.createCell(wb, row, 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("awdName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("year")) {
                ExcelUtils.createCell(wb, row, 5, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("year").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("proName")) {
                ExcelUtils.createCell(wb, row, 6, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("proName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("proTypeName")) {
                ExcelUtils.createCell(wb, row, 7, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("proTypeName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("issueDate")) {
                ExcelUtils.createCell(wb, row, 8, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("issueDate").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("sdf")) {
                ExcelUtils.createCell(wb, row, 9, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("sdf").toString()); //要充满屏幕又要中间
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

    private List<Map<String, Object>> doWeight(List<Map<String, Object>> list) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Integer count = tbCompanyAwardsMapper.queryAwardsCount(map);
            if(count <= 0){
                resultList.add(map);
            }
        }
        return resultList;
    }
}
