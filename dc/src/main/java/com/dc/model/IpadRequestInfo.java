package com.dc.model;

import com.dc.web.controller.RequestXml;

public class IpadRequestInfo extends BaseModel {

    private String userName;
    // 桌号
    private String tableId;
    // 用餐人数
    private String caps;
    private String password;
    private String sid;

    public IpadRequestInfo(String tableId, String caps) {
        super();
        this.tableId = tableId;
        this.caps = caps;
    }

    public String getUserName() {
        return userName;
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
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getSid() {
        return sid;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }
}
