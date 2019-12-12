package com.silita.dao;

import com.silita.model.TbReportInfo;
import com.silita.utils.MyMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * tb_report_info
 */
@Repository
public interface TbReportInfoMapper extends MyMapper<TbReportInfo> {


    /**
     * 获取报告标题
     * @param orderNo
     * @return
     */
    String queryRepTitle(String orderNo);

}