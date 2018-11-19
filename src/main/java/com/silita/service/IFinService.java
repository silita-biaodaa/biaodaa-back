package com.silita.service;

import com.silita.model.TbFinService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Map;

/**
 * 金融服务
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-11-19 9:48
 */
public interface IFinService {

    /**
     * 获取金融服务列表信息
     * @param tbFinService
     * @return
     */
    Map<String, Object> listFinService(TbFinService tbFinService);

    /**
     * 导出金融服务列表信息
     * @param tbFinService
     * @return
     */
    public HSSFWorkbook exportFinService(TbFinService tbFinService) throws Exception;
}
