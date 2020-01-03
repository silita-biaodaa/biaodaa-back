package com.silita.service;

import com.github.pagehelper.PageInfo;
import com.silita.model.DicAlias;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 别名service
 */
public interface IAliasService {

    /**
     * 查询别名
     *
     * @param param
     * @return
     */
    Map<String, Object> gitAliasListStdCode(Map<String, Object> param);

    /**
     * 删除别名
     *
     * @param param
     */
    Map<String, Object> delAilasByIds(Map<String, Object> param);

    /**
     * 根据id删除别名
     *
     * @param param
     * @return
     */
    Map<String, Object> delAilasById(Map<String, Object> param);


    /**
     * 批量导入别名信息
     *
     * @param sheet    excel页
     * @param stdCode  别名code
     * @param fileName 文件名
     * @return
     */
    Map<String, Object> insertAilas(Sheet sheet, String stdCode, String fileName) throws IOException, Exception;

    /**
     * 添加等级别名
     *
     * @param param
     * @return
     */
    Map<String, Object> insertLevelAilas(Map<String, Object> param);



}
