package com.cse.summer.model.dto;

import java.util.List;

/**
 * 分页控制类
 *
 * @author 王振琦
 * createAt 2018/09/19
 * updateAt 2021/09/15
 */
public class PageContext<T> {
    /**
     * 页码
     */
    private int pageIndex;
    /**
     * 每页数据量
     */
    private int pageSize;
    /**
     * 数据总量
     */
    private long dataTotal;
    /**
     * 页面总数
     */
    private int pageTotal;
    /**
     * 数据
     */
    private List<T> data;

    public PageContext() {
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getDataTotal() {
        return dataTotal;
    }

    public void setDataTotal(long dataTotal) {
        this.dataTotal = dataTotal;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
