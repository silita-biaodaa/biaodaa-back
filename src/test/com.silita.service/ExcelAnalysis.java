package com.silita.service;

import com.silita.dao.SysAreaMapper;
import com.silita.model.SysArea;
import com.silita.utils.DataHandlingUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelAnalysis extends ConfigTest {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Test
    public void test() throws IOException {
        {
            File file = new File("E:\\Download\\sqlå\u0092\u008Cexcel\\sql和excel\\2017年最新行政区划数据库-旗舰版.xls");
            FileInputStream inputStream = new FileInputStream(file);
            String fileName = file.getName();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(inputStream);
            List<SysArea> areaList = new ArrayList<>();
            SysArea area = null;
            if (null != hssfWorkbook) {
                Sheet sheet = hssfWorkbook.getSheetAt(0);
                int rowCount = sheet.getLastRowNum();
                Row row = null;
                Cell cell = null;
                Map<String, Object> param = new HashMap<>();
                for (int i = 1; i <= rowCount; i++) {
                    area = new SysArea();
                    row = null;
                    row = sheet.getRow(i);
                    cell = row.getCell(3);
                    String areaStr = cell.getStringCellValue();
                    String[] areas = areaStr.split(",");
                    if (areas.length != 3) {
                        continue;
                    }
                    if (!"北京市".equals(areas[2]) && !"天津市".equals(areas[2]) && !"上海市".equals(areas[2]) && !"重庆市".equals(areas[2])) {
                        continue;
                    }
                    SysArea sysArea = sysAreaMapper.queryAreaByName(areas[1]);
                    if (null == sysArea) {
                        continue;
                    }
                    area.setAreaParentId(sysArea.getPkid());
                    area.setPkid(DataHandlingUtil.getUUID());
                    area.setAreaName(areas[2]);
                    cell = row.getCell(9);
                    area.setAreaShortName(area.getAreaName());
                    area.setAreaCode(cell.getStringCellValue().toLowerCase());
                    area.setAreaLevel(2);
                    area.setCreateBy("gemingyi");
                    area.setCreated(new Date());
                    areaList.add(area);
                }
            }
            if (null != areaList && areaList.size() > 0) {
                add(areaList);
            }
        }
    }


    private void add(List<SysArea> areaList) {
        for (SysArea area:areaList) {
            sysAreaMapper.insertArea(area);
        }
    }
}