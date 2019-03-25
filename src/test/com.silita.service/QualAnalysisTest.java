package com.silita.service;

import com.silita.common.Constant;
import com.silita.dao.DicCommonMapper;
import com.silita.dao.DicQuaMapper;
import com.silita.model.DicCommon;
import com.silita.model.DicQua;
import com.silita.model.RelQuaGrade;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class QualAnalysisTest extends ConfigTest {

    @Autowired
    IGradeService gradeService;
    @Autowired
    IQualService qualService;
    @Autowired
    DicQuaMapper dicQuaMapper;
    @Autowired
    IRelQuaGradeService relQuaGradeService;
    @Autowired
    DicCommonMapper dicCommonMapper;

    //    String[] strings = new String[]{"甲级", "乙级", "丙级", "丁级", "一级", "二级", "三级", "四级", "五级"};
    String[] strings = new String[]{"特级"};

    @Test
    public void test() {
        for (String str : strings) {
            DicCommon dicCommon = new DicCommon();
            dicCommon.setName(str);
            gradeService.saveGrade(dicCommon, null);
        }
    }

    @Test
    public void quaCateTest() throws IOException {
        File file = new File("F:\\Company\\耀邦\\20190322资质分类.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            String value = cell.getStringCellValue();
            if (null != value && !"".equals(value)) {
                DicQua dicQua = new DicQua();
                dicQua.setQuaName(value);
                dicQua.setBizType(Constant.BIZ_TYPE_ALL);
                if (value.contains("企业无")) {
                    dicQua.setBizType(Constant.BIZ_TYPE_NOTIC);
                }
                qualService.addQual(dicQua, null);
            }
            System.out.println("------------------value:" + value + "------------------");
        }
    }

    @Test
    public void quaChildTest() throws IOException {
        File file = new File("F:\\Company\\耀邦\\20190322资质分类.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(1);
            String value = cell.getStringCellValue();
            Cell parentCell = row.getCell(0);
            String parentValue = parentCell.getStringCellValue();
            Cell childCell = row.getCell(2);
            String childValue = childCell.getStringCellValue();
            if (StringUtils.isEmpty(childValue)) {
                Cell cell5 = row.getCell(4);
                String jiaji = cell5.getStringCellValue();
                Cell cell6 = row.getCell(5);
                String yiji = cell6.getStringCellValue();
                Cell cell7 = row.getCell(6);
                String binji = cell7.getStringCellValue();
                Cell cell8 = row.getCell(7);
                String dinji = cell8.getStringCellValue();
                Cell cell9 = row.getCell(8);
                String teji = cell9.getStringCellValue();
                Cell cell10 = row.getCell(9);
                String yji = cell10.getStringCellValue();
                Cell cell11 = row.getCell(10);
                String erji = cell11.getStringCellValue();
                Cell cell12 = row.getCell(11);
                String sanji = cell12.getStringCellValue();
                Cell cell13 = row.getCell(12);
                Cell cell14 = row.getCell(13);
                String siji = null;
                String wuji = null;
                if (null != cell13) {
                    siji = cell13.getStringCellValue();
                }
                if (null != cell14) {
                    wuji = cell14.getStringCellValue();
                }
                addQualGrade(value, parentValue, jiaji, yiji, binji, dinji, teji, yji, erji, sanji, siji, wuji, null);
            } else {
                addQualGrade(value, parentValue, null, null, null, null, null, null, null, null, null, null, null);
            }
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }
    }

    @Test
    public void quaTest() throws IOException {
        File file = new File("F:\\Company\\耀邦\\20190322资质分类.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(2);
            String value = cell.getStringCellValue();
            Cell parentCell = row.getCell(1);
            String parentValue = parentCell.getStringCellValue();
            Cell childCell = row.getCell(0);
            String childValue = childCell.getStringCellValue();
            Cell siCell = row.getCell(3);
            String siValue = siCell.getStringCellValue();
            if (StringUtils.isNotEmpty(value)) {
                if (StringUtils.isNotEmpty(siValue)) {
                    addQualGrade(value, parentValue, null, null, null, null, null, null, null, null, null, null, childValue);
                    continue;
                }
                Cell cell5 = row.getCell(4);
                String jiaji = cell5.getStringCellValue();
                Cell cell6 = row.getCell(5);
                String yiji = cell6.getStringCellValue();
                Cell cell7 = row.getCell(6);
                String binji = cell7.getStringCellValue();
                Cell cell8 = row.getCell(7);
                String dinji = cell8.getStringCellValue();
                Cell cell9 = row.getCell(8);
                String teji = cell9.getStringCellValue();
                Cell cell10 = row.getCell(9);
                String yji = cell10.getStringCellValue();
                Cell cell11 = row.getCell(10);
                String erji = cell11.getStringCellValue();
                Cell cell12 = row.getCell(11);
                String sanji = cell12.getStringCellValue();
                Cell cell13 = row.getCell(12);
                Cell cell14 = row.getCell(13);
                String siji = null;
                String wuji = null;
                if (null != cell13) {
                    siji = cell13.getStringCellValue();
                }
                if (null != cell14) {
                    wuji = cell14.getStringCellValue();
                }
                addQualGrade(value, parentValue, jiaji, yiji, binji, dinji, teji, yji, erji, sanji, siji, wuji, childValue);
            }
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }
    }

    @Test
    public void quaSiTest() throws IOException {
        File file = new File("F:\\Company\\耀邦\\20190322资质分类.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(3);
            String value = cell.getStringCellValue();
            Cell parentCell = row.getCell(2);
            String parentValue = parentCell.getStringCellValue();
            Cell childCell = row.getCell(1);
            String childValue = childCell.getStringCellValue();
            Cell siCell = row.getCell(0);
            String siValue = siCell.getStringCellValue();
            if (StringUtils.isNotEmpty(value)) {
                Cell cell5 = row.getCell(4);
                String jiaji = cell5.getStringCellValue();
                Cell cell6 = row.getCell(5);
                String yiji = cell6.getStringCellValue();
                Cell cell7 = row.getCell(6);
                String binji = cell7.getStringCellValue();
                Cell cell8 = row.getCell(7);
                String dinji = cell8.getStringCellValue();
                Cell cell9 = row.getCell(8);
                String teji = cell9.getStringCellValue();
                Cell cell10 = row.getCell(9);
                String yji = cell10.getStringCellValue();
                Cell cell11 = row.getCell(10);
                String erji = cell11.getStringCellValue();
                Cell cell12 = row.getCell(11);
                String sanji = cell12.getStringCellValue();
                Cell cell13 = row.getCell(12);
                Cell cell14 = row.getCell(13);
                String siji = null;
                String wuji = null;
                if (null != cell13) {
                    siji = cell13.getStringCellValue();
                }
                if (null != cell14) {
                    wuji = cell14.getStringCellValue();
                }
                addQualSiGrade(value, parentValue, jiaji, yiji, binji, dinji, teji, yji, erji, sanji, siji, wuji, childValue, siValue);
            }
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }
    }

    public void addQualGrade(String... strings) {
        String name;
        String qua = strings[0];
        String quaParent = strings[1];
        String childValue = strings[12];
        String jiaji = strings[2];
        String yiji = strings[3];
        String binji = strings[4];
        String dinji = strings[5];
        String teji = strings[6];
        String yji = strings[7];
        String erji = strings[8];
        String sanji = strings[9];
        String siji = strings[10];
        String wuji = strings[11];
        if ("承装".equals(qua) || "承修".equals(qua) || "承试".equals(qua) || "城市园林绿化".equals(qua) || "施工劳务".equals(qua)) {
        } else {
            if (StringUtils.isNotEmpty(childValue)) {
                if (quaParent.contains(childValue)) {
                    quaParent = quaParent.replace(childValue, "");
                }
            } else {
                if (qua.contains(quaParent)) {
                    qua = qua.replace(quaParent, "");
                }
            }
        }
        if (StringUtils.isNotEmpty(childValue)) {
            name = childValue;
        } else {
            name = quaParent;
        }

        DicQua resultParent = dicQuaMapper.queryQualDetailParentName(name);
        DicQua qua1 = new DicQua();
        qua1.setQuaName(qua);
        qua1.setParentId(resultParent.getId());
        qua1.setBizType(resultParent.getBizType());
        if (StringUtils.isNotEmpty(childValue))

        {
            qua1 = new DicQua();
            qua1.setQuaName(quaParent);
            qua1.setParentId(resultParent.getId());
            DicQua resultPQua = dicQuaMapper.queryQualDetailName(qua1);
            qua1 = new DicQua();
            qua1.setParentId(resultPQua.getId());
            qua1.setQuaName(qua);
            if (StringUtils.isNotEmpty(childValue)) {
                qua1.setLevel("3");
            }
            qua1.setBizType(resultPQua.getBizType());
        }
        qualService.addQual(qua1, null);
        DicQua resultQua = dicQuaMapper.queryQualDetailName(qua1);
        RelQuaGrade relQuaGrade = new RelQuaGrade();
        relQuaGrade.setQuaCode(resultQua.getQuaCode());
        relQuaGrade.setBizType(Constant.BIZ_TYPE_ALL);
        StringBuffer grade = new StringBuffer();
        if (StringUtils.isNotEmpty(jiaji))

        {
            if ("甲（Ⅰ）级、甲（Ⅱ）级".equals(jiaji)) {
                jiaji = "甲级";
            }
            grade.append("|").append(dicCommonMapper.queryQuaGrade(jiaji).getCode());
        }
        if (StringUtils.isNotEmpty(yiji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(yiji).getCode());
        }
        if (StringUtils.isNotEmpty(binji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(binji).getCode());
        }
        if (StringUtils.isNotEmpty(dinji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(dinji).getCode());
        }
        if (StringUtils.isNotEmpty(teji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(teji).getCode());
        }
        if (StringUtils.isNotEmpty(yji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(yji).getCode());
        }
        if (StringUtils.isNotEmpty(erji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(erji).getCode());
        }
        if (StringUtils.isNotEmpty(sanji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(sanji).getCode());
        }
        if (StringUtils.isNotEmpty(siji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(siji).getCode());
        }
        if (StringUtils.isNotEmpty(wuji))

        {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(wuji).getCode());
        }
        relQuaGrade.setGradeCode(grade.toString());
        if (StringUtils.isNotEmpty(grade))

        {
            relQuaGradeService.addQuaGrade(relQuaGrade);
        }
    }

    public void addQualSiGrade(String... strings) {
        String qua = strings[0];
        String quaParent = strings[1];
        String childValue = strings[12];
        String siValue = strings[13];
        String jiaji = strings[2];
        String yiji = strings[3];
        String binji = strings[4];
        String dinji = strings[5];
        String teji = strings[6];
        String yji = strings[7];
        String erji = strings[8];
        String sanji = strings[9];
        String siji = strings[10];
        String wuji = strings[11];
        if ("承装".equals(qua) || "承修".equals(qua) || "承试".equals(qua) || "城市园林绿化".equals(qua) || "施工劳务".equals(qua)) {
        } else {
            if (childValue.contains(siValue)) {
                childValue = childValue.replace(siValue, "");
            }
        }
        DicQua resultParent = dicQuaMapper.queryQualDetailParentName(siValue);
        DicQua qua1 = new DicQua();
        qua1.setQuaName(childValue);
        qua1.setParentId(resultParent.getId());
        DicQua resultParent2 = dicQuaMapper.queryQualDetailName(qua1);
        qua1 = new DicQua();
        qua1.setParentId(resultParent2.getId());
        qua1.setQuaName(quaParent);
        DicQua resultParent3 = dicQuaMapper.queryQualDetailName(qua1);
        qua1 = new DicQua();
        qua1.setParentId(resultParent3.getId());
        qua1.setQuaName(qua);
        qua1.setBizType(resultParent3.getBizType());
        qua1.setLevel("4");
        qualService.addQual(qua1, null);
        DicQua resultQua = dicQuaMapper.queryQualDetailName(qua1);
        RelQuaGrade relQuaGrade = new RelQuaGrade();
        relQuaGrade.setQuaCode(resultQua.getQuaCode());
        relQuaGrade.setBizType(Constant.BIZ_TYPE_ALL);
        StringBuffer grade = new StringBuffer();
        if (StringUtils.isNotEmpty(jiaji)) {
            if ("甲（Ⅰ）级、甲（Ⅱ）级".equals(jiaji)) {
                jiaji = "甲级";
            }
            grade.append("|").append(dicCommonMapper.queryQuaGrade(jiaji).getCode());
        }
        if (StringUtils.isNotEmpty(yiji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(yiji).getCode());
        }
        if (StringUtils.isNotEmpty(binji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(binji).getCode());
        }
        if (StringUtils.isNotEmpty(dinji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(dinji).getCode());
        }
        if (StringUtils.isNotEmpty(teji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(teji).getCode());
        }
        if (StringUtils.isNotEmpty(yji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(yji).getCode());
        }
        if (StringUtils.isNotEmpty(erji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(erji).getCode());
        }
        if (StringUtils.isNotEmpty(sanji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(sanji).getCode());
        }
        if (StringUtils.isNotEmpty(siji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(siji).getCode());
        }
        if (StringUtils.isNotEmpty(wuji)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(wuji).getCode());
        }
        relQuaGrade.setGradeCode(grade.toString());
        if (StringUtils.isNotEmpty(grade)) {
            relQuaGradeService.addQuaGrade(relQuaGrade);
        }
    }
}
