package com.silita.utils;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.collections.map.HashedMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhushuai on 2020/5/8.
 */
public class PageUtils {

    /**
     * 获取总页数
     *
     * @param total    总条数
     * @param pageSize 页面大小
     * @return
     */
    public static int getPages(long total, int pageSize) {
        int pages = 0;
        if (total % pageSize == 0) {
            pages = (int) total / pageSize;
            return pages;
        }
        return (int) total / pageSize + 1;
    }

    /**
     *  分页显示
     * @param list
     * @param pageSize
     * @param pageNo
     * @return
     */
    public static Map<String,Object> getPageData(List list,int pageSize,int pageNo){
        Map<String, Object> resultMap = new HashedMap(3) {{
            put("total", 0);
            put("pages", 0);
            put("data", new ArrayList<>(1));
        }};
        if (CollUtil.isEmpty(list)) {
            return resultMap;
        }
        int pages = PageUtils.getPages(list.size(), pageSize);
        if (pageNo > pages) {
            pageNo = pages;
        }
        resultMap.put("total",list.size());
        resultMap.put("pages",pages);
        resultMap.put("data",PageUtils.startPage(list,pageNo,pageSize));
        return resultMap;
    }

    /**
     * 开始分页
     * @param list
     * @param pageNum 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static List startPage(List list, int pageNum,
                                 int pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        int count = list.size(); // 记录总数
        int pageCount = 0; // 页数
        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        int fromIndex = 0; // 开始索引
        int toIndex = 0; // 结束索引
        if (pageNum != pageCount) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex, toIndex);
        return pageList;
    }
}
