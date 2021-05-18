package com.demo.entity;

/**
 * @author dzy
 * @date 2021/5/12
 * @desc 分页参数
 */
public class PageParam {

    private int pageNum = 1;

    private int pageSize = 20;

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
}
