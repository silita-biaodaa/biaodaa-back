package com.silita.service.abs;

import com.silita.common.Constant;
import com.silita.utils.split.Pagination;
import org.apache.poi.ss.formula.functions.T;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public abstract class AbstractService {
    /**
     * 分页
     *
     * @param page
     * @return
     */
    public Map<String, Object> handleParams(Pagination<? extends Pagination> page) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", page.getStart());
        params.put("pageSize", page.getPageSize());
        return params;
    }

    /**
     * 计算共多少页
     *
     * @param params
     * @param page
     * @return
     */
    public Map<String, Object> handlePageCount(Map<String, Object> params, Pagination<? extends Pagination> page) {
        int pageCount = 0;
        int total = (Integer) params.get("total");
        if (total % page.getPageSize() == 0) {
            pageCount = total / page.getPageSize();
        } else {
            pageCount = total / page.getPageSize() + 1;
        }
        params.put("pageCount", pageCount);
        params.put("pageSize", page.getPageSize());
        params.put("currentPage", page.getCurrentPage());
        return params;
    }

    /**
     * 计算共多少页
     *
     * @param params
     * @param
     * @return
     */
    public Map<String, Object> handlePageCount(Map<String, Object> params, Integer total, Integer pageSize, Integer currentPage) {
        int pageCount = 0;
        if (total % pageSize == 0) {
            pageCount = total / pageSize;
        } else {
            pageCount = total / pageSize + 1;
        }
        params.put("pageCount", pageCount);
        params.put("pageSize", pageSize);
        params.put("currentPage", currentPage);
        return params;
    }





    /**
     *  * @currPageNo  页面传入的页号，从一开始
     *  * @pageSize    每页记录数
     *  
     */
    public <T> Map<String, Object> getPagingResultMap(List<T> list, Integer currPageNo, Integer pageSize) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code", Constant.CODE_SUCCESS);
        retMap.put("msg",Constant.MSG_SUCCESS);
        if (list.isEmpty()) {
            retMap.put("data", Collections.emptyList());
            retMap.put("pageNo", 0);
            retMap.put("pageSize", 0);
            retMap.put("total", 0);
            retMap.put("pages", 0);

            return retMap;
        }

        int totalRowNum = list.size();
        int totalPageNum = (totalRowNum - 1) / pageSize + 1;

        int realPageNo = currPageNo;
        if (currPageNo > totalPageNum) {
            realPageNo = totalPageNum;
        } else if (currPageNo < 1) {
            realPageNo = 1;
        }

        int fromIdx = (realPageNo - 1) * pageSize;
        int toIdx = realPageNo * pageSize > totalRowNum ? totalRowNum : realPageNo * pageSize;

        List<T> result = list.subList(fromIdx, toIdx);

        retMap.put("data", result);
        retMap.put("pageNo", realPageNo);
        retMap.put("pageSize", result.size());
        retMap.put("total", totalRowNum);
        retMap.put("pages", totalPageNum);

        return retMap;
    }






}
