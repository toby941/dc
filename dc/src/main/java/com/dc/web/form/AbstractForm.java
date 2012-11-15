package com.dc.web.form;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * AbstractForm.java
 */
public abstract class AbstractForm {
    // 处理动作
    private String action;
    // 验证码
    private String verifyCode;
    // 页面跳转标志
    private String xview = "default";
    private String nextUrl;
    private String remoteAddr;
    private String currentPage;
    private String pageSize;
    private String totalCount;
    /** 降序标志 */
    private String desc;
    /** 升序标志 */
    private String asce;
    // 升降序标志 desc OR asc
    private String order;
    // 所需排序字段名
    private String sortname;
    // 上一个页面的页数、排序位，适用于从一个列表页到下一个列表页再返回的情况
    private String prePage;
    private String preOrderBy;
    // 用于保存查询内容的变量
    private String searchStr;
    //
    private String orderField;
    private String orderBy;
    //
    private String title;
    private String actionurl;

    //

    /**
     * @return the searchStr
     */
    public String getSearchStr() {
        return searchStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionurl() {
        return actionurl;
    }

    public void setActionurl(String actionurl) {
        this.actionurl = actionurl;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    /**
     * @param searchStr the searchStr to set
     */
    public void setSearchStr(String searchStr) {
        this.searchStr = searchStr;
    }

    /**
     * @return the prePage
     */
    public String getPrePage() {
        return prePage;
    }

    /**
     * @param prePage the prePage to set
     */
    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    /**
     * @return the preOrderBy
     */
    public String getPreOrderBy() {
        return preOrderBy;
    }

    /**
     * @param preOrderBy the preOrderBy to set
     */
    public void setPreOrderBy(String preOrderBy) {
        this.preOrderBy = preOrderBy;
    }

    public AbstractForm() {
        super();
        currentPage = "1";
        pageSize = 10 + "";
    }

    /**
     * @return the totalCount
     */
    public String getTotalCount() {
        return totalCount;
    }

    /**
     * @return the currentPage
     */
    public String getCurrentPage() {
        String returnPage = StringUtils.trimToEmpty(currentPage);
        if (NumberUtils.isNumber(returnPage)) {
            return returnPage;
        }
        else {
            return "1";
        }
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @param totalCount the totalCount to set
     */
    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    // 错误信息，保证错误信息按顺序输出
    private Map<String, String> errors = new LinkedHashMap<String, String>();
    // controller与service之间的数据传递
    private Map<String, Object> values = new LinkedHashMap<String, Object>();

    /**
     * @return the verifyCode
     */
    public String getVerifyCode() {
        return verifyCode;
    }

    /**
     * @param verifyCode the verifyCode to set
     */
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    /**
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return the xview
     */
    public String getXview() {
        return xview;
    }

    /**
     * @param xview the xview to set
     */
    public void setXview(String xview) {
        this.xview = xview;
    }

    /**
     * @return the nextUrl
     */
    public String getNextUrl() {
        return nextUrl;
    }

    /**
     * @param nextUrl the nextUrl to set
     */
    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    /**
     * @return the remoteAddr
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * @param remoteAddr the remoteAddr to set
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
     * @return the pageSize
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }

    /**
     * @param errors the errors to set
     */
    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    /**
     * @return the values
     */
    public Map<String, Object> getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(Map<String, Object> values) {
        this.values = values;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return the asce
     */
    public String getAsce() {
        return asce;
    }

    /**
     * @param asce the asce to set
     */
    public void setAsce(String asce) {
        this.asce = asce;
    }

    public abstract void form2domain();

    /**
     * 排序只有两种取值 asc desc 此处进行安全过滤
     * 
     * @return the order
     */
    public String getOrder() {
        if ("ASC".equals(order)) {
            return "ASC";
        }
        else {
            return "DESC";
        }
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     * @return the sortname
     */
    public String getSortname() {
        return sortname;
    }

    /**
     * @param sortname the sortname to set
     */
    public void setSortname(String sortname) {
        this.sortname = sortname;
    }
}
