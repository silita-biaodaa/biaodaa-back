package com.silita.service.impl;

import com.silita.common.Constant;
import com.silita.common.ConstantMap;
import com.silita.dao.SysAreaMapper;
import com.silita.dao.TbCompanyHighwayGradeMapper;
import com.silita.dao.TbCompanyMapper;
import com.silita.model.TbCompanyHighwayGrade;
import com.silita.service.ICompanyHighwayGradeService;
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
 * tb_company_highway_grade service
 */
@Service
public class CompanyHighwayGradeServiceImpl extends AbstractService implements ICompanyHighwayGradeService {

    @Autowired
    TbCompanyHighwayGradeMapper tbCompanyHighwayGradeMapper;
    @Autowired
    SysAreaMapper sysAreaMapper;
    @Autowired
    TbCompanyMapper companyMapper;
    @Autowired
    PropertiesUtils propertiesUtils;

    @Override
    public List<Map<String, Object>> getCompanyHighwayGradeList(TbCompanyHighwayGrade grade) {
        List<Map<String, Object>> list = tbCompanyHighwayGradeMapper.queryCompanyHigway(grade);
        String[] assessLevels = null;
        String[] assessOrigins = null;
        List<Map<String, Object>> companyHighwayList = new ArrayList<>();
        Map<String, Object> highwayMap = null;
        if (null != list && list.size() > 0) {
            for (Map<String, Object> map : list) {
                highwayMap = new HashMap<>();
                assessLevels = MapUtils.getString(map, "assessLevel").split(",");
                assessOrigins = MapUtils.getString(map, "assessOrigin").split(",");
                if (null != assessLevels && assessLevels.length > 0) {
                    for (int i = 0; i < assessOrigins.length; i++) {
                        if (Constant.SOURCE_PRO.equals(assessOrigins[i])) {
                            highwayMap.put("proAssessLevel", assessLevels[i]);
                            continue;
                        }
                        highwayMap.put("labAssessLevel", assessLevels[i]);
                        continue;
                    }
                }
                highwayMap.put("assessProv", sysAreaMapper.queryAreaName(MapUtils.getString(map, "assessProvCode")));
                highwayMap.put("comId", MapUtils.getString(map, "comId"));
                highwayMap.put("assessYear", MapUtils.getString(map, "assessYear"));
                highwayMap.put("assessProvCode", MapUtils.getString(map, "assessProvCode"));
                companyHighwayList.add(highwayMap);
            }
        }
        return companyHighwayList;
    }

    @Override
    public List<Map<String, Object>> getHighwayProv(Map<String, Object> param) {
        return tbCompanyHighwayGradeMapper.queryAssessPro(MapUtils.getString(param, "comId"));
    }

    @Override
    public Map<String, Object> addHighway(TbCompanyHighwayGrade grade, String username) {
        //判断是否存在
        grade.setAssessOrigin(Constant.SOURCE_LAB);
        TbCompanyHighwayGrade companyHighwayGrade = tbCompanyHighwayGradeMapper.queryCompanyHighwanGradeDetail(grade);
        if (null != companyHighwayGrade) {
            tbCompanyHighwayGradeMapper.deleteCompanyHigway(companyHighwayGrade.getPkid());
        }
        grade.setPkid(DataHandlingUtil.getUUID());
        grade.setCreateBy(username);
        grade.setCreated(new Date());
        grade.setAssessOrigin(Constant.SOURCE_LAB);
        tbCompanyHighwayGradeMapper.insertCompanyHigway(grade);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    @Override
    public Map<String, Object> getCompanyHighwayGradeForCompanyList(Map<String, Object> param) {
        TbCompanyHighwayGrade grade = new TbCompanyHighwayGrade();
        grade.setComName(MapUtils.getString(param, "comName"));
        grade.setAssessLevel(MapUtils.getString(param, "assessLevel"));
        grade.setAssessProvCode(MapUtils.getString(param, "assessProvCode"));
        grade.setAssessYear(MapUtils.getInteger(param, "assessYear"));
        grade.setProvince(MapUtils.getString(param, "province"));
        grade.setCurrentPage(MapUtils.getInteger(param, "currentPage"));
        grade.setPageSize(MapUtils.getInteger(param, "pageSize"));
        Map<String, Object> resultMap = new HashMap<>();
        Integer total = tbCompanyHighwayGradeMapper.queryCompanyHigForCompanyCount(grade);
        if (total <= 0) {
            return null;
        }
        resultMap.put("list", tbCompanyHighwayGradeMapper.queryCompanyHigForCompanyList(grade));
        resultMap.put("total", total);
        return super.handlePageCount(resultMap, grade);
    }

    @Override
    public void deleteCompanyHigwagGrade(Map<String, Object> param) {
        String pkids = MapUtils.getString(param, "pkids");
        String[] pkidList = pkids.split("\\|");
        for (String pkid : pkidList) {
            tbCompanyHighwayGradeMapper.deleteCompanyHigway(pkid);
        }
    }

    @Override
    public Map<String, Object> batchExportCompanyHighwayGrade(Sheet sheet, String username, String fileName) throws IOException {
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
            excelMap = new HashMap<>();
            sbf = new StringBuffer();
            //企业名称
            cell = row.getCell(0);
            if (null != cell && null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
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
            //省
            cell = row.getCell(1);
            if (null != cell && null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
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
            //年度
            cell = row.getCell(2);
            if (null != cell && null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                excelMap.put("year", cell.getStringCellValue());
            }
            //等级
            cell = row.getCell(3);
            if (null != cell && null != cell.getStringCellValue() && !"".equals(cell.getStringCellValue())) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                excelMap.put("level", cell.getStringCellValue());
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
            String fileUrl = uploadExcel(excelList, fileName);
            resultMap.put("code", Constant.CODE_WARN_405);
            resultMap.put("msg", Constant.MSG_WARN_405);
            resultMap.put("data", fileUrl);
            return resultMap;
        }
        List<TbCompanyHighwayGrade> list = doWeight(excelList);
        if (null != list && list.size() > 0) {
            tbCompanyHighwayGradeMapper.batchInsertCompanyHig(list);
        }
        resultMap.put("code", Constant.CODE_SUCCESS);
        resultMap.put("msg", Constant.MSG_SUCCESS);
        return resultMap;
    }

    private String uploadExcel(List<Map<String, Object>> excelList, String fileName) throws IOException {
        XSSFWorkbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("公路信用等级");  // 创建第一个Sheet页;
        Row row = sheet.createRow(0); // 创建一个行
        row.setHeightInPoints(30); //设置这一行的高度
        ExcelUtils.createCell(wb, row, (short) 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "企业名称"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "省"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "年度"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "等级"); //要充满屏幕又要中间
        ExcelUtils.createCell(wb, row, (short) 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, "错误原因"); //要充满屏幕又要中间
        for (int i = 0; i < excelList.size(); i++) {
            row = sheet.createRow(i + 1); // 创建一个行
            row.setHeightInPoints(30); //设置这一行的高度
            if (null != excelList.get(i).get("comName")) {
                ExcelUtils.createCell(wb, row, 0, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("comName").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("prov")) {
                ExcelUtils.createCell(wb, row, 1, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("prov").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("year")) {
                ExcelUtils.createCell(wb, row, 2, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("year").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("level")) {
                ExcelUtils.createCell(wb, row, 3, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("level").toString()); //要充满屏幕又要中间
            }
            if (null != excelList.get(i).get("sdf")) {
                ExcelUtils.createCell(wb, row, 4, CellStyle.ALIGN_FILL, CellStyle.VERTICAL_CENTER, excelList.get(i).get("sdf").toString()); //要充满屏幕又要中间
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

    private List<TbCompanyHighwayGrade> doWeight(List<Map<String, Object>> excelList) {
        List<TbCompanyHighwayGrade> resultList = new ArrayList<>();
        TbCompanyHighwayGrade tbCompanyHighwayGrade;
        for (Map<String, Object> map : excelList) {
            Integer count = tbCompanyHighwayGradeMapper.queryCount(map);
            if (count <= 0) {
                tbCompanyHighwayGrade = new TbCompanyHighwayGrade();
                tbCompanyHighwayGrade.setPkid(MapUtils.getString(map,"pkid"));
                tbCompanyHighwayGrade.setComId(MapUtils.getString(map,"comId"));
                tbCompanyHighwayGrade.setAssessYear(MapUtils.getInteger(map,"year"));
                tbCompanyHighwayGrade.setAssessLevel(MapUtils.getString(map,"level"));
                tbCompanyHighwayGrade.setAssessProvCode(MapUtils.getString(map,"provCode"));
                tbCompanyHighwayGrade.setCreateBy(MapUtils.getString(map,"createdBy"));
                resultList.add(tbCompanyHighwayGrade);
            }
        }
        return resultList;
    }
}
