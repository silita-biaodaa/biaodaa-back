package com.silita.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    public static void createCell(XSSFWorkbook wb, Row row, int column, short halign, short valign, String val) {
        Cell cell = row.createCell(column);  // 创建单元格
        cell.setCellValue(new XSSFRichTextString(val));  // 设置值
    }
}
