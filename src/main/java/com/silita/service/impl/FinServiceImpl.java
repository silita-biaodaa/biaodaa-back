package com.silita.service.impl;

import com.silita.dao.TbFinServiceMapper;
import com.silita.model.TbFinService;
import com.silita.service.IFinService;
import com.silita.service.abs.AbstractService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 金融服务
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-11-19 9:48
 */
@Service("finService")
public class FinServiceImpl extends AbstractService implements IFinService {

    @Autowired
    private TbFinServiceMapper tbFinServiceMapper;

    @Override
    public Map<String, Object> listFinService(TbFinService tbFinService) {
        Map result = new HashMap<String, Object>();
        result.put("datas", tbFinServiceMapper.listFinService(tbFinService));
        result.put("total", tbFinServiceMapper.countFinService(tbFinService));
        return super.handlePageCount(result, tbFinService);
    }

    @Override
    public HSSFWorkbook exportFinService(TbFinService tbFinService) throws Exception{
        int indexRow = 0;
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row = sheet.createRow(indexRow++);
        String[] headers = {
                "项目名称", "开标时间", "项目地区", "借款人名称", "借款人电话号码",
                "借款时间", "申请金额", "提交时间"
        };
        //创建标题
        for (int i = 0; i < headers.length; i++) {
            row.createCell(i).setCellValue(headers[i]);
        }
        List<TbFinService> tbFinServiceList = tbFinServiceMapper.listAllFinService(tbFinService);
        TbFinService tempTbFinService;
        //一行数据
        try {
            for (int i = 0; i < tbFinServiceList.size(); i++) {
                int indexCell = 0;
                row = sheet.createRow(indexRow++);
                tempTbFinService = tbFinServiceList.get(i);
                //一列数据
                for (Field field : tempTbFinService.getClass().getDeclaredFields()) {
                    String name = field.getName();
                    //这些字段不要
                    if("pkid".equals(name) || "userId".equals(name) || "createBy".equals(name) || "createdTwo".equals(name) || "status".equals(name)) {
                        continue;
                    }
                    name = name.substring(0, 1).toUpperCase() + name.substring(1);
                    HSSFCell cell = row.createCell(indexCell++);
                    String type = field.getGenericType().toString();
                    Method m = null;
                    if ("class java.lang.String".equals(type)) {
                        m = tempTbFinService.getClass().getDeclaredMethod("get" + name);
                        cell.setCellValue((String) m.invoke(tempTbFinService));
                    }
                    if ("class java.lang.Double".equals(type)) {
                        m = tempTbFinService.getClass().getDeclaredMethod("get" + name);
                        cell.setCellValue((Double) m.invoke(tempTbFinService));
                    }
                    if ("class java.util.Date".equals(type)) {
                        m = tempTbFinService.getClass().getDeclaredMethod("get" + name);
                        cell.setCellValue(new Date(Long.parseLong((String) m.invoke(tempTbFinService))));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return wb;
    }
}
