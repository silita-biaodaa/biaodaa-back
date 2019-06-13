package com.silita.service.impl;

import com.silita.dao.TbNtTextHunanMapper;
import com.silita.model.TbNtText;
import com.silita.service.INtContentService;
import org.apache.commons.collections.MapUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * tb_nt_text serviceimpl
 */
@Service
public class NtContentServiceImpl implements INtContentService {

    @Autowired
    TbNtTextHunanMapper tbNtTextHunanMapper;


    private String hBaseTableName="nocite";

    @Autowired
    private Connection connection;

    @Override
    public TbNtText getNtContent(TbNtText ntText) {
        TbNtText nt = tbNtTextHunanMapper.queryNtTextDetail(ntText);
        if (null == nt) {
            return new TbNtText();
        }
        return nt;
    }

    /**
     * 获取公告详情
     *
     * @param param // 爬取id
     *              返回String类型
     * @return
     * @throws IOException
     */

    public String queryCentent(Map<String, Object> param) throws IOException {
        String snatchId = MapUtils.getString(param, "snatchId");
        String content = "";
        Map<String, Object> map = new HashMap<String, Object>();
        TableName tableName = TableName.valueOf(hBaseTableName);
        Table table = connection.getTable(tableName);
        Get g = new Get(snatchId.getBytes());
        Result rs = table.get(g);
        Cell[] cells = rs.rawCells();
        for (Cell cell : cells) {
            String key = Bytes.toString(CellUtil.cloneQualifier(cell));
            String value = Bytes.toString(CellUtil.cloneValue(cell));
            switch (key) {
                case "content": //获取内容
                    content = value;
                    break;
            }
        }
        return content;
    }

}
