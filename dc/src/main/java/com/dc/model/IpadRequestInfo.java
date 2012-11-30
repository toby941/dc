package com.dc.model;

import org.apache.commons.lang.StringUtils;

import com.dc.web.controller.RequestXml;

public class IpadRequestInfo extends BaseModel {

    private String userName;
    // 桌号
    private String tableId;
    // 用餐人数
    private String caps;
    private String password;
    private String sid;

    private String type;
    private String status;
    private String time;
    private String booker;

    public String getType() {
        return StringUtils.trimToEmpty(type);
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return StringUtils.trimToEmpty(status);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return StringUtils.trimToEmpty(time);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBooker() {
        return StringUtils.trimToEmpty(booker);
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getTel() {
        return StringUtils.trimToEmpty(tel);
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    private String tel;

    public IpadRequestInfo(String tableId, String caps) {
        super();
        this.tableId = tableId;
        this.caps = caps;
    }

    public String getUserName() {
        return fillLength(userName, 4, true);
    }

    public static String fillLength(String str, Integer length, boolean leftBlank) {
        str = StringUtils.trimToEmpty(str);
        if (str.length() >= length) {
            return str;
        }
        String blank = "";
        for (int i = 0; i < length; i++) {
            blank = blank + " ";
        }
        if (leftBlank) {
            return blank + str;
        } else {
            return str + blank;
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTableId() {
        if (tableId == null) {
            return "0";
        }
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public IpadRequestInfo() {
        super();
    }

    public IpadRequestInfo(RequestXml requestXml) {
        this.userName = requestXml.getParamValue("Username");
        this.caps = requestXml.getParamValue("Caps");
        this.tableId = requestXml.getParamValue("TableId");
        this.password = requestXml.getParamValue("Password");
        sid = System.nanoTime() + "";
    }

    public String getCaps() {
        if (caps == null) {
            return "0";
        }
        return caps;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public String getPassword() {
        return fillLength(password, 8, false);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSid() {
        return StringUtils.trimToEmpty(sid);
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }
}
