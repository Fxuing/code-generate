package com.fxuing.codegenerate.common;

import java.io.Serializable;

public class Paginate<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private long total;
    private int currentPageNum = 1;
    private T data;
    private int recordsPerPage = 10;
    private int showTags = 5;
    private Object condition;
    private static final boolean oracle;

    static {
        boolean recognize = false;
        oracle = recognize;
    }

    public long getPageTotal() {
        return (this.total + this.recordsPerPage - 1L) / this.recordsPerPage;
    }

    public int getStartNum() {
        if (this.currentPageNum <= 1) {
            return oracle ? 1 : 0;
        }
        return (this.currentPageNum - 1) * this.recordsPerPage + (oracle ? 1 : 0);
    }

    public int getEndNum() {
        return getStartNum() - 1 + this.recordsPerPage;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getCurrentPageNum() {
        return this.currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getRecordsPerPage() {
        return this.recordsPerPage;
    }

    public int getShowTags() {
        return this.showTags;
    }

    public void setShowTags(int showTags) {
        this.showTags = showTags;
    }

    public Object getCondition() {
        return this.condition;
    }

    public void setCondition(Object condition) {
        this.condition = condition;
    }

    public void setRecordsPerPage(int recordsPerPage) {
        this.recordsPerPage = recordsPerPage;
    }
}