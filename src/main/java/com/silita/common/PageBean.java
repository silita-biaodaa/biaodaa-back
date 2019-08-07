package com.silita.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PageBean {
    /**
     * 对集合进行分页的方法
     * @param personList
     * @param basePageModel
     * @return
     */
    public static   List<Map<String,Object>> getPageListMap(List<Map<String,Object>> personList, BasePageModel basePageModel) {
        List<Map<String,Object>> pageList = new ArrayList();
        int page = basePageModel.getPage();//当前页数
        int rows = basePageModel.getRows();//每页显示多少行数据
        int dataRows = personList.size();//总行数
        int totalPages = (dataRows % rows == 0) ? (dataRows / rows) : (dataRows / rows + 1) ; //总页数

        int pageStartRows ;//起始行数
        int pageEndRows;//结束行数

        if (page * rows < dataRows) {
            pageEndRows = page * rows;
            pageStartRows = pageEndRows - rows;
        } else {
            pageEndRows = dataRows;
            pageStartRows = rows * (totalPages - 1);
        }
        for (int i = pageStartRows; i < pageEndRows; i++) {
            pageList.add(personList.get(i));
        }
        return pageList;
    }

    public static   List<LinkedHashMap<String,Object>> getPageList(List<LinkedHashMap<String,Object>> personList, BasePageModel basePageModel) {
        List<LinkedHashMap<String,Object>> pageList = new ArrayList();
        int page = basePageModel.getPage();//当前页数
        int rows = basePageModel.getRows();//每页显示多少行数据
        int dataRows = personList.size();//总行数
        int totalPages = (dataRows % rows == 0) ? (dataRows / rows) : (dataRows / rows + 1) ; //总页数

        int pageStartRows ;//起始行数
        int pageEndRows;//结束行数

        if (page * rows < dataRows) {
            pageEndRows = page * rows;
            pageStartRows = pageEndRows - rows;
        } else {
            pageEndRows = dataRows;
            pageStartRows = rows * (totalPages - 1);
        }
        for (int i = pageStartRows; i < pageEndRows; i++) {
            pageList.add(personList.get(i));
        }
        return pageList;
    }
}
