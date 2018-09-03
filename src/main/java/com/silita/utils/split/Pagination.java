package com.silita.utils.split;

import java.io.Serializable;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-08-09 15:19
 */
public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 20180810L;

    private int pageSize = 15;// 每页大小
    private int currentPage = 1;// 当前第几页
    private int start = 0;  // 起始位置

    public int getPageSize() {
        if (pageSize > 60) {
            pageSize = 60;
        }
        if (pageSize <= 0) {
            pageSize = 15;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getStart() {
        start = (currentPage - 1) * pageSize;
        if (start < 0) {
            start = 0;
        }
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}
