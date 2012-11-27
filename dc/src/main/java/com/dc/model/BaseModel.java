/*
 * Copyright 2011 Mitian Technology, Co., Ltd. All rights reserved.
 */
package com.dc.model;

import org.apache.commons.lang.StringUtils;

/**
 * BaseModel.java
 */
public abstract class BaseModel {

    /**
     * 列表查询的数量限制
     */
    private int limit;
    /**
     * 分页偏移量码
     */
    private int offset;

    /**
     * 排序字段
     */
    private String sortName;
    /**
     * 降序还是升序
     */
    private String sortType;

    /**
     * @return the limit
     */
    public int getLimit() {
        return limit;
    }

    /**
     * @param limit the limit to set
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the sortName
     */
    public String getSortName() {
        return sortName;
    }

    /**
     * @param sortName the sortName to set
     */
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    /**
     * @return the sortType
     */
    public String getSortType() {
        if (StringUtils.endsWithIgnoreCase("desc", StringUtils.trimToEmpty(sortType))) {
            return "desc";
        }
        return "asc";
    }

    /**
     * @param sortType the sortType to set
     */
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + limit;
        result = prime * result + offset;
        result = prime * result + ((sortName == null) ? 0 : sortName.hashCode());
        result = prime * result + ((sortType == null) ? 0 : sortType.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BaseModel other = (BaseModel) obj;
        if (limit != other.limit) {
            return false;
        }
        if (offset != other.offset) {
            return false;
        }
        if (sortName == null) {
            if (other.sortName != null) {
                return false;
            }
        }
        else if (!sortName.equals(other.sortName)) {
            return false;
        }
        if (sortType == null) {
            if (other.sortType != null) {
                return false;
            }
        }
        else if (!sortType.equals(other.sortType)) {
            return false;
        }
        return true;
    }

    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

}
