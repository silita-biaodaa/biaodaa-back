package com.silita.service;

import com.silita.common.Constant;
import com.silita.dao.TbCompanyMapper;
import com.silita.dao.TbCompanyQualificationMapper;
import com.silita.model.DicQua;
import com.silita.model.TbCompanyQualification;
import com.silita.utils.DataHandlingUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhushuai on 2019/7/24.
 */
public class GongLuQualTest extends ConfigTest {

    @Autowired
    TbCompanyQualificationMapper tbCompanyQualificationMapper;
    @Autowired
    TbCompanyMapper tbCompanyMapper;

    @Test
    public void test() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\地质灾害.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();
        for (int i = 0; i <= rows; i++) {
            TbCompanyQualification qualification = new TbCompanyQualification();
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            Cell qualCell = row.getCell(1);
            String comName = cell.getStringCellValue();
            String qualName = qualCell.getStringCellValue();
            Map<String,Object> qualMap = tbCompanyQualificationMapper.queryCompanyQualType(comName);
            if (MapUtils.isNotEmpty(qualMap)){
                qualification.setPkid(MapUtils.getString(qualMap,"pkid"));
                String resQualName = MapUtils.getString(qualMap,"qual_name");
                StringBuffer stringBuffer = new StringBuffer(resQualName);
                stringBuffer.append(",").append(qualName);
                qualification.setQualName(stringBuffer.toString());
                qualification.setRange(stringBuffer.toString());
                tbCompanyQualificationMapper.updateCompanyQual(qualification);
                continue;
            }
            String comId = tbCompanyMapper.queryComIdByName(comName);
            qualification.setPkid(DataHandlingUtil.getUUID());
            qualification.setComId(comId);
            qualification.setComName(comName);
            qualification.setQualName(qualName);
            qualification.setRange(qualName);
            qualification.setChannel(3);
            qualification.setTab("建筑企业");
            qualification.setQualType("地质灾害");
            Cell certOrgCell = row.getCell(2);
            qualification.setCertOrg(certOrgCell.getStringCellValue());
            qualification.setIsValid(1);
            tbCompanyQualificationMapper.insertCompanyQual(qualification);
        }
    }


    @Test
    public void gongluTest() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\湖南省2016年-2018年度公路养护资质准入认定通过企业名录汇总表（已核对）(1) - 副本.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet1");
        int rows = sheet.getLastRowNum();
        for (int i = 0; i <= rows; i++) {
            TbCompanyQualification qualification = new TbCompanyQualification();
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            Cell qualCell = row.getCell(1);
            String comName = cell.getStringCellValue();
            String qualName = qualCell.getStringCellValue();
            String comId = tbCompanyMapper.queryComIdByName(comName);
            qualification.setPkid(DataHandlingUtil.getUUID());
            qualification.setComId(comId);
            qualification.setComName(comName);
            qualification.setQualName(qualName);
            qualification.setRange(qualName);
            qualification.setChannel(3);
            qualification.setTab("建筑企业");
            qualification.setQualType("公路养护");
            qualification.setCertOrg("湖南省交通运输厅");
            qualification.setIsValid(1);
            tbCompanyQualificationMapper.insertCompanyQual(qualification);
        }
    }

    @Test
    public void cpu(){
        Integer num = Runtime.getRuntime().availableProcessors();
        System.out.println(num);
    }
}
