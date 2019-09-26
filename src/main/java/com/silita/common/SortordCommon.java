package com.silita.common;

import com.silita.model.DicQua;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 排序
 */
public class SortordCommon {

    public static String getSortord(DicQua dicQua,String code){
        String rank = dicQua.getRank();
        if (StringUtil.isEmpty(rank)) {
            dicQua.setRank("create_time");
            dicQua.setSort("desc");
        } else {
            String sort = dicQua.getSort();
            if (rank.equals("时间")) {
                dicQua.setRank("create_time");
                if (sort.equals("降序")) {
                    dicQua.setSort("desc");
                } else {
                    dicQua.setSort("asc");
                }
            } else {
                dicQua.setRank(code);
                if (sort.equals("降序")) {
                    dicQua.setSort("desc");
                } else {
                    dicQua.setSort("asc");
                }
            }
        }
        return dicQua.getRank()+" "+dicQua.getSort();
    }
}
