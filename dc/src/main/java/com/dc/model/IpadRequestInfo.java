package com.dc.model;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.dc.web.controller.RequestXml;

public class IpadRequestInfo extends BaseModel {

    public static String fillLength(String str, Integer length, boolean leftBlank) {
        str = StringUtils.trimToEmpty(str);
        if (str.length() >= length) {
            return str;
        }
        String blank = "";
        for (int i = 0; i < length - str.length(); i++) {
            blank = blank + " ";
        }
        if (leftBlank) {
            return blank + str;
        } else {
            return str + blank;
        }
    }

    public static void main(String[] args) {
        String s = "002";
        System.out.println(fillLength(s, 5, false));
    }
    private String booker;
    // 用餐人数
    private String caps;
    private List<CourseTab> courseTabs;
    private String password;

    private String sid;
    private String status;
    // 桌号
    private String tableId;

    private String tel;

    private String time;

    private String type;

    private String userName;

    // 实际用餐人数 OpenTable 命令携带：GuestNum；TableId
    private String guestNum;
    // 客户端mac地址 Login命令携带：Username；Password；MacAddr
    private String macAddr;

    public IpadRequestInfo() {
        super();
    }

    public IpadRequestInfo(RequestXml requestXml) {
        this.userName = requestXml.getParamValue("Username");
        this.caps = requestXml.getParamValue("Caps");
        this.tableId = requestXml.getParamValue("TableId");
        this.password = requestXml.getParamValue("Password");
        this.macAddr = requestXml.getParamValue("MacAddr");
        sid = System.nanoTime() + "";
        this.time = DateFormatUtils.format(Calendar.getInstance(), "HH:mm:ss");

    }

    public void addParams(RequestXml requestXml) {
        String gvenCaps = requestXml.getParamValue("Caps");
        String givenTableId = requestXml.getParamValue("TableId");
        String givenGuestNum = requestXml.getParamValue("GuestNum");
        caps = StringUtils.defaultIfEmpty(gvenCaps, caps);
        tableId = StringUtils.defaultIfEmpty(givenTableId, tableId);
        guestNum = StringUtils.defaultIfEmpty(givenGuestNum, guestNum);
    }

    public IpadRequestInfo(String tableId, String caps) {
        super();
        this.tableId = tableId;
        this.caps = caps;
    }

    public String getBooker() {
        return StringUtils.trimToEmpty(booker);
    }

    public String getCaps() {
        if (caps == null) {
            caps = "6";
        }
        return fillLength(caps, 2, false);
    }

    public List<CourseTab> getCourseTabs() {
        return courseTabs;
    }

    public String getPassword() {
        return fillLength(password, 8, false);
    }

    @Override
    public String getSid() {
        return StringUtils.trimToEmpty(sid);
    }

    public String getStatus() {
        return StringUtils.trimToEmpty(status);
    }

    public String getTableId() {
        if (tableId == null) {
            tableId = "0";
        }

        return fillLength(tableId, 7, false);
    }

    public String getOpenTableId() {
        if (tableId == null) {
            tableId = "0";
        }

        return fillLength(tableId, 4, false);
    }

    public String getTel() {
        return StringUtils.trimToEmpty(tel);
    }

    public String getTime() {
        return StringUtils.trimToEmpty(time);
    }

    public String getType() {
        return StringUtils.trimToEmpty(type);
    }

    public String getUserName() {
        return fillLength(userName, 5, false);
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public void setCourseTabs(List<CourseTab> courseTabs) {
        this.courseTabs = courseTabs;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
