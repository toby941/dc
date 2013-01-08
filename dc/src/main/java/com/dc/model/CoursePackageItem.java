package com.dc.model;

import java.util.List;

/**
 * 菜品套餐明细项
 * 
 * @author toby
 */
public class CoursePackageItem {
    /**
     * id+courseNo
     */
    private String idShow;

    public String getIdShow() {
        return idShow;
    }

    public void setIdShow(String idShow) {
        this.idShow = idShow;
    }

    private String id;
    /**
     * 菜品编号(5)
     */
    private String courseNo;
    /**
     * 数量(9)
     */
    private String courseCount;

    /**
     * 中文名称(20)
     */
    private String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 单价(9)
     */
    private String coursePrice;
    /**
     * 单位(4)
     */
    private String courseUnit;
    /**
     * 缺省选中标志（1位） 点套餐的时候，缺省显示“缺省选中标志”为1的套餐明细。
     */
    private String flag;
    /**
     * 菜品相关图片文件
     */
    private List<CourseFile> files;

    public List<CourseFile> getFiles() {
        return files;
    }

    public void setFiles(List<CourseFile> files) {
        this.files = files;
    }

    public CoursePackageItem(String id, String courseNo, String count, String priceAndUnit) {
        this.id = id;
        this.courseNo = courseNo;
        this.idShow = id + courseNo;
        this.courseCount = count;
        this.coursePrice = priceAndUnit.substring(0, priceAndUnit.length() - 1);
        this.courseUnit = priceAndUnit.substring(priceAndUnit.length() - 1, priceAndUnit.length());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
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

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCoursePackageGroup() {
        return coursePackageGroup;
    }

    public void setCoursePackageGroup(String coursePackageGroup) {
        this.coursePackageGroup = coursePackageGroup;
    }

    /**
     * 套餐菜组号（2位）
     */
    private String coursePackageGroup;

}
