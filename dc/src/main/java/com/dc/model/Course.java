package com.dc.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 菜肴<br/>
 * 菜品编号(5)类别号(2)中文名称(20)单价(9)单位(4)重量单位(4)需要确认重量否(1)制作要求(45)拼音编码(10)<br/>
 * 0100101凉拌黄瓜 6.00份 份 0 LBHG
 * 
 * @author toby
 */
public class Course {
    /**
     * 制作要求(45)
     */
    private String courseCookingrequest;
    /**
     * 数量
     */
    private String courseCount;
    /**
     * 中文名称(20)
     */
    private String courseName;
    /**
     * 菜品编号(5)
     */
    private String courseNo;
    /**
     * 拼音编码(10)
     */
    private String coursePingyin;
    /**
     * 单价(9)
     */
    private String coursePrice;
    /**
     * 类别号(2)
     */
    private String courseType;
    /**
     * 菜品种类描述
     */
    private String courseTypeStr;
    /**
     * 单位(4)
     */
    private String courseUnit;
    /**
     * 需要确认重量否(1)
     */
    private String courseWeightConfirm;
    /**
     * 重量单位(4)
     */
    private String courseWeightUnit;
    /**
     * 菜品描述 ipad显示用
     */
    private String desc;
    /**
     * 菜品相关图片文件
     */
    private List<CourseFile> files;
    /**
     * 总价=coursePrice*courseCount
     */
    private String sumPrice;

    /**
     * 上菜状态
     */
    private String status;

    public String getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice = sumPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Course() {
        super();
        this.status = "0";
    }

    /**
     * 根据上位软件返回 构造已点菜单详情
     * 
     * @param name
     * @param count
     * @param price
     * @param sumPrice
     */
    public Course(String name, String count, String price, String sumPrice) {
        super();
        this.courseName = name;
        this.courseCount = count;
        this.coursePrice = price;
        this.sumPrice = sumPrice;
        this.status = "0";
    }

    /**
     * 解析本地菜单文件 正则构造菜单列表
     * 
     * @param no
     * @param type
     * @param name
     * @param price
     * @param unit
     * @param weightConfirm
     * @param pingyin
     */
    public Course(String no, String type, String name, String price, String unit, String weightConfirm, String pingyin) {
        super();
        this.courseNo = no;
        this.courseType = type;
        this.courseName = name;
        this.coursePrice = price;
        this.courseUnit = unit;
        this.courseWeightConfirm = weightConfirm;
        this.coursePingyin = pingyin;

    }

    public String getCourseCookingrequest() {
        return courseCookingrequest;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public String getCoursePingyin() {
        return coursePingyin;
    }

    public String getCoursePrice() {
        if (StringUtils.isNotBlank(coursePrice)) {
            return coursePrice.substring(0, coursePrice.length() - 1);
        }
        else {
            return "";
        }
    }

    public String getCourseType() {
        return courseType;
    }

    public String getCourseTypeStr() {
        return courseTypeStr;
    }

    public String getCourseUnit() {
        return courseUnit;
    }

    public String getCourseWeightConfirm() {
        return courseWeightConfirm;
    }

    public String getCourseWeightUnit() {
        return courseWeightUnit;
    }

    public String getDesc() {
        return desc;
    }

    public List<CourseFile> getFiles() {
        return files;
    }

    public void setCourseCookingrequest(String courseCookingrequest) {
        this.courseCookingrequest = courseCookingrequest;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public void setCoursePingyin(String coursePingyin) {
        this.coursePingyin = coursePingyin;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public void setCourseTypeStr(String courseTypeStr) {
        this.courseTypeStr = courseTypeStr;
    }

    public void setCourseUnit(String courseUnit) {
        this.courseUnit = courseUnit;
    }

    public void setCourseWeightConfirm(String courseWeightConfirm) {
        this.courseWeightConfirm = courseWeightConfirm;
    }

    public void setCourseWeightUnit(String courseWeightUnit) {
        this.courseWeightUnit = courseWeightUnit;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFiles(List<CourseFile> files) {
        this.files = files;
    }
}
