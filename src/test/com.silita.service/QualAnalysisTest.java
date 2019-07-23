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
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;
import static com.sun.tools.doclint.Entity.nu;
import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;

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
    String[] strings = new String[]{"二级及以上", "三级及以上", "四级及以上", "五级及以上", "乙级及以上", "丙级及以上", "丁级及以上", "一级级以上"};

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
        File file = new File("E:\\朱帅\\耀邦\\20190326资质分类（补全资质标准名称）.xlsx");
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
        File file = new File("E:\\朱帅\\耀邦\\20190326资质分类（补全资质标准名称）.xlsx");
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
            addQual(value, parentValue);
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }
    }

    @Test
    public void quaTest() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\20190326资质分类（补全资质标准名称）.xlsx");
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
            Cell grandfatherCell = row.getCell(0);
            String grandfatherValue = grandfatherCell.getStringCellValue();
            if (StringUtils.isNotEmpty(value)) {
                addQual2(value, parentValue, grandfatherValue);
            }
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }

    }

    @Test
    public void quaSiTest() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\20190326资质分类（补全资质标准名称）.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(3);
            String siValue = "";
            if (null != cell) {
                siValue = cell.getStringCellValue();
            }
            Cell parentCell = row.getCell(2);
            String sanValue = "";
            if (null != parentCell) {
                sanValue = parentCell.getStringCellValue();
            }
            Cell childCell = row.getCell(1);
            String erValue = "";
            if (null != childCell) {
                erValue = childCell.getStringCellValue();
            }
            Cell siCell = row.getCell(0);
            String yiValue = "";
            if (null != siCell) {
                yiValue = siCell.getStringCellValue();
            }
            if (StringUtils.isNotEmpty(siValue)) {
                addQual3(siValue, sanValue, erValue, yiValue);
            }
            System.out.println("------------------value:" + value + "--parent:" + parentCell.getStringCellValue() + "----------------");
        }
    }

    @Test
    public void quaWuTest() throws IOException {
        File file = new File("E:\\朱帅\\耀邦\\20190326资质分类（补全资质标准名称）.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheet("Sheet3");
        int rows = sheet.getLastRowNum();
        for (int i = 1; i <= rows; i++) {
            Row row = sheet.getRow(i);
            Cell zeroCell = row.getCell(0);
            String zeroValue = "";
            if (null != zeroCell) {
                zeroValue = zeroCell.getStringCellValue();
            }
            Cell oneCell = row.getCell(1);
            String oneValue = "";
            if (null != oneCell) {
                oneValue = oneCell.getStringCellValue();
            }
            Cell twoCell = row.getCell(2);
            String twoValue = "";
            if (null != twoCell) {
                twoValue = twoCell.getStringCellValue();
            }
            Cell threeCell = row.getCell(3);
            String threeValue = "";
            if (null != threeCell) {
                threeValue = threeCell.getStringCellValue();
            }
            Cell fourCell = row.getCell(4);
            String fourValue = "";
            if (null != fourCell) {
                fourValue = fourCell.getStringCellValue();
            }
            Cell fiveCell = row.getCell(5);
            String fiveValuue = "";
            if (null != fiveCell) {
                fiveValuue = fiveCell.getStringCellValue();
            }
            Cell sixCell = row.getCell(6);
            String sixValue = "";
            if (null != sixCell) {
                sixValue = sixCell.getStringCellValue();
            }
            Cell sevenCell = row.getCell(7);
            String sevenValue = "";
            if (null != sevenCell) {
                sevenValue = sevenCell.getStringCellValue();
            }
            Cell eightCell = row.getCell(8);
            String eightValue = "";
            if (null != eightCell) {
                eightValue = eightCell.getStringCellValue();
            }
            Cell nineCell = row.getCell(9);
            String nineValue = "";
            if (null != nineCell) {
                nineValue = nineCell.getStringCellValue();
            }
            Cell tenCell = row.getCell(10);
            String tenValue = "";
            if (null != tenCell) {
                tenValue = tenCell.getStringCellValue();
            }
            Cell elevenCell = row.getCell(11);
            String elevenValue = "";
            if (null != elevenCell) {
                elevenValue = elevenCell.getStringCellValue();
            }
            Cell twelveCell = row.getCell(12);
            String twelveValue = "";
            if (null != twelveCell) {
                twelveValue = twelveCell.getStringCellValue();
            }
            Cell thirteenCell = row.getCell(13);
            String thirteenValue = "";
            if (null != thirteenCell) {
                thirteenValue = thirteenCell.getStringCellValue();
            }
            Cell fourteenCell = row.getCell(14);
            String fourteenValue = "";
            if (null != fourteenCell) {
                fourteenValue = fourteenCell.getStringCellValue();
            }
            Cell fifteenCell = row.getCell(15);
            String fifteenValue = "";
            if (null != fifteenCell) {
                fifteenValue = fifteenCell.getStringCellValue();
            }
            Cell sixteenCell = row.getCell(16);
            String sixteenValue = "";
            if (null != sixteenCell) {
                sixteenValue = sixteenCell.getStringCellValue();
            }
            Cell seventeenCell = row.getCell(17);
            String seventeenValue = "";
            if (null != seventeenCell) {
                seventeenValue = seventeenCell.getStringCellValue();
            }
            Cell eighteenCell = row.getCell(18);
            String eighteenValue = "";
            if (null != eighteenCell) {
                eighteenValue = eighteenCell.getStringCellValue();
            }
            addQualSiGrade(zeroValue, oneValue, twoValue, threeValue, fourValue, fiveValuue, sixValue, sevenValue, eightValue, nineValue, tenValue, elevenValue,
                    twelveValue, thirteenValue, fourteenValue, fifteenValue, sixteenValue, seventeenValue, eighteenValue);
        }
    }

    public void addQual(String... strings) {
        String qua = strings[0];
        String quaParent = strings[1];
        DicQua resultParent = dicQuaMapper.queryQualDetailParentName(quaParent);
        DicQua qua1 = new DicQua();
        qua1.setQuaName(qua);
        qua1.setParentId(resultParent.getId());
        qua1.setBizType(Constant.BIZ_TYPE_ALL);
        if ("安防".equals(qua)) {
            qua1.setBizType(Constant.BIZ_TYPE_COMPANY);
        } else if ("城市园林绿化".equals(qua)) {
            qua1.setBizType(Constant.BIZ_TYPE_NOTIC);
        }
        qualService.addQual(qua1, null);
    }

    public void addQual2(String... strings) {
        String qua = strings[0];
        String quaParent = strings[1];
        String grandfather = strings[2];
        DicQua resultParent = dicQuaMapper.queryQualDetailParentName(grandfather);
        DicQua dicQua = new DicQua();
        dicQua.setQuaName(quaParent);
        dicQua.setParentId(resultParent.getId());
        DicQua parent = dicQuaMapper.queryQualDetailName(dicQua);
        DicQua qua1 = new DicQua();
        qua1.setQuaName(qua);
        qua1.setParentId(parent.getId());
        qua1.setBizType(Constant.BIZ_TYPE_ALL);
        qua1.setLevel("3");
        if (qua.contains("安防")) {
            qua1.setBizType(Constant.BIZ_TYPE_COMPANY);
        } else if ("城市园林绿化".equals(qua)) {
            qua1.setBizType(Constant.BIZ_TYPE_NOTIC);
        }
        qualService.addQual(qua1, null);
    }

    public void addQual3(String... strings) {
        String qua = strings[0];
        String quaParent = strings[1];
        String childValue = strings[2];
        String siValue = strings[3];
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
    }

    public void addQualSiGrade(String... strings) {
        String zeroValue = strings[0];
        String oneValue = strings[1];
        String twoValue = strings[2];
        String threeValue = strings[3];
        String fourValue = strings[4];
        String fiveValuue = strings[5];
        String sixValuue = strings[6];
        String sevenValuue = strings[7];
        String eightValuue = strings[8];
        String nineValuue = strings[9];
        String tenValuue = strings[10];
        String elevenValuue = strings[11];
        String twelveValuue = strings[12];
        String thirteenValuue = strings[13];
        String fourteenValuue = strings[14];
        String fifteenValuue = strings[15];
        String sixteenValuue = strings[16];
        String seventeenValuue = strings[17];
        String eighteenValuue = strings[18];
        String pkid = "";
        String quaCode = "";
        if (StringUtils.isEmpty(twoValue) && StringUtils.isEmpty(threeValue)) {
            DicQua result1 = dicQuaMapper.queryQualDetailParentName(zeroValue);
            DicQua dicQua1 = new DicQua();
            dicQua1.setQuaName(oneValue);
            dicQua1.setParentId(result1.getId());
            DicQua result2 = dicQuaMapper.queryQualDetailName(dicQua1);
            pkid = result2.getId();
            quaCode = result2.getQuaCode();
        } else {
            DicQua result1 = dicQuaMapper.queryQualDetailParentName(zeroValue);
            DicQua dicQua1 = new DicQua();
            dicQua1.setQuaName(oneValue);
            dicQua1.setParentId(result1.getId());
            DicQua result2 = dicQuaMapper.queryQualDetailName(dicQua1);
            dicQua1 = new DicQua();
            dicQua1.setQuaName(twoValue);
            dicQua1.setParentId(result2.getId());
            DicQua result3 = dicQuaMapper.queryQualDetailName(dicQua1);
            if (StringUtils.isNotEmpty(threeValue)) {
                dicQua1 = new DicQua();
                dicQua1.setQuaName(threeValue);
                dicQua1.setParentId(result3.getId());
                DicQua result4 = dicQuaMapper.queryQualDetailName(dicQua1);
                pkid = result4.getId();
                quaCode = result4.getQuaCode();
            } else {
                pkid = result3.getId();
                quaCode = result3.getQuaCode();
            }
        }

        StringBuffer grade = new StringBuffer();
        if (StringUtils.isNotEmpty(fiveValuue)) {
            if ("甲（Ⅰ）级、甲（Ⅱ）级".equals(fiveValuue)) {
                fiveValuue = "甲级";
            }
            grade.append("|").append(dicCommonMapper.queryQuaGrade(fiveValuue).getCode());
        }
        if (StringUtils.isNotEmpty(sixValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(sixValuue).getCode());
        }
        if (StringUtils.isNotEmpty(sevenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(sevenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(eightValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(eightValuue).getCode());
        }
        if (StringUtils.isNotEmpty(nineValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(nineValuue).getCode());
        }
        if (StringUtils.isNotEmpty(tenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(tenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(elevenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(elevenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(twelveValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(twelveValuue).getCode());
        }
        if (StringUtils.isNotEmpty(thirteenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(thirteenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(fourteenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(fourteenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(fifteenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(fifteenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(sixteenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(sixteenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(seventeenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(seventeenValuue).getCode());
        }
        if (StringUtils.isNotEmpty(eighteenValuue)) {
            grade.append("|").append(dicCommonMapper.queryQuaGrade(eighteenValuue).getCode());
        }
        DicQua dic = new DicQua();
        dic.setId(pkid);
        dic.setBenchName(fourValue);
        dicQuaMapper.updateQual(dic);
        RelQuaGrade relQuaGrade = new RelQuaGrade();
        relQuaGrade.setGradeCode(grade.toString());
        relQuaGrade.setQuaCode(quaCode);
        if (StringUtils.isNotEmpty(grade)) {
            relQuaGradeService.addQuaGrade(relQuaGrade);
        }
    }

    @Test
    public void qualNotGrade() {
        File file = new File("E:\\朱帅\\耀邦\\资质不分等级.xlsx");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheet("Sheet1");
            int rows = sheet.getLastRowNum();
            for (int i = 1; i <= rows; i++) {
                Row row = sheet.getRow(i);
                Cell cell = row.getCell(4);
                if (null != cell){
                    String qualCode = dicQuaMapper.queryQualCodeByBenchName(cell.getStringCellValue());
                    if (StringUtils.isNotEmpty(qualCode)){
                        RelQuaGrade relQuaGrade = new RelQuaGrade();
                        relQuaGrade.setQuaCode(qualCode);
                        relQuaGrade.setGradeCode("0");
                        relQuaGradeService.addQuaGrade(relQuaGrade);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addQualGonglu(){
//        DicQua qua = new DicQua();
//        qua.setBenchName("公路养护计算机系统维护");
//        qua.setParentId("6fc925f5b8464cdd9edc28bb24f40793");
//        qua.setBizType("0");
//        qua.setLevel("2");
//        qua.setQuaName("计算机系统维护");
//        qualService.addQual(qua,null);

        String qualCode = dicQuaMapper.queryQualCodeByBenchName("公路养护机电专项");
        if (StringUtils.isNotEmpty(qualCode)){
            RelQuaGrade relQuaGrade = new RelQuaGrade();
            relQuaGrade.setQuaCode(qualCode);
            relQuaGrade.setGradeCode("0");
            relQuaGradeService.addQuaGrade(relQuaGrade);
        }
    }
}
