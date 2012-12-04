package com.dc.model;

/**
 * 菜肴<br/>
 * 菜品编号(5)类别号(2)中文名称(20)单价(9)单位(4)重量单位(4)需要确认重量否(1)制作要求(45)拼音编码(10)<br/>
 * 0100101凉拌黄瓜 6.00份 份 0 LBHG
 * 
 * @author toby
 */
public class Course {
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getCourseUnit() {
        return courseUnit;
    }

    public void setCourseUnit(String courseUnit) {
        this.courseUnit = courseUnit;
    }

    public String getCourseWeightUnit() {
        return courseWeightUnit;
    }

    public void setCourseWeightUnit(String courseWeightUnit) {
        this.courseWeightUnit = courseWeightUnit;
    }

    public String getCourseWeightConfirm() {
        return courseWeightConfirm;
    }

    public void setCourseWeightConfirm(String courseWeightConfirm) {
        this.courseWeightConfirm = courseWeightConfirm;
    }

    public String getCourseCookingrequest() {
        return courseCookingrequest;
    }

    public void setCourseCookingrequest(String courseCookingrequest) {
        this.courseCookingrequest = courseCookingrequest;
    }

    public String getCoursePingyin() {
        return coursePingyin;
    }

    public void setCoursePingyin(String coursePingyin) {
        this.coursePingyin = coursePingyin;
    }

    // 菜品编号(5)
    private String courseNo;
    // 类别号(2)
    private String courseType;
    // 中文名称(20)
    private String courseName;
    // 单价(9)
    private String coursePrice;
    // 单位(4)
    private String courseUnit;
    // 重量单位(4)
    private String courseWeightUnit;
    // 需要确认重量否(1)
    private String courseWeightConfirm;
    // 制作要求(45)
    private String courseCookingrequest;
    // 拼音编码(10)
    private String coursePingyin;

    private String courseTypeStr;

    public String getCourseTypeStr() {
        return courseTypeStr;
    }

    public void setCourseTypeStr(String courseTypeStr) {
        this.courseTypeStr = courseTypeStr;
    }

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

    public Course() {
        super();
    }
}
