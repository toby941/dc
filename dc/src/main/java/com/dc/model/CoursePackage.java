package com.dc.model;

import java.util.List;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 套餐类别表
 * 
 * @author toby
 */
public class CoursePackage {
    private String id;
    private String coursePackageName;

    public List<CourseFile> getFiles() {
        return files;
    }

    public void setFiles(List<CourseFile> files) {
        this.files = files;
    }

    private List<CourseFile> files;
    private List<CoursePackageItem> coursePackageItems;

    public CoursePackage() {
        super();
    }

    public List<CoursePackageItem> getCoursePackageItems() {
        return coursePackageItems;
    }

    public void setCoursePackageItems(List<CoursePackageItem> coursePackageItems) {
        this.coursePackageItems = coursePackageItems;
    }

    public CoursePackage(String id, String name) {
        super();
        this.id = id;
        this.coursePackageName = name;
    }

    public String getSumCost() {
        Double sumCost = 0.0;
        for (CoursePackageItem item : coursePackageItems) {
            String price = item.getCoursePrice();
            String count = item.getCourseCount();
            if (NumberUtils.isNumber(price) && NumberUtils.isDigits(count)) {
                Double cost = Double.parseDouble(price) * Integer.parseInt(count);
                sumCost += cost;
            }
        }
        return String.valueOf(sumCost);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCoursePackageName() {
        return StringUtils.trimToEmpty(coursePackageName);
    }

    public void setCoursePackageName(String coursePackageName) {
        this.coursePackageName = coursePackageName;
    }
}
