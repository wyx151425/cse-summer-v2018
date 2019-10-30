package com.cse.summer.model.entity;

import java.util.List;

/**
 * 分页控制类
 *
 * @author 王振琦
 * createAt 2018/09/19
 * updateAt 2018/10/12
 */
public class PageContext<T> {
    private int pageNum;
    private int pageSize;
    private long total;
    private int pages;
    private List<T> data;

    public PageContext() {
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
