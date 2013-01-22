package com.dc.model;

import java.util.List;

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
