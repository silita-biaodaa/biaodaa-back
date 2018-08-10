package com.silita.service.abs;

import com.silita.utils.split.Pagination;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public class AbstractService {
    /**
     * 分页
     * @param page
     * @return
     */
    public Map<String, Object> handleParams(Pagination<? extends Pagination> page) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("start", page.getStart());
        params.put("pageSize", page.getPageSize());
        return params;
    }
}
