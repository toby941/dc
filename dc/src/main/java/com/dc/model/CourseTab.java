package com.dc.model;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 菜品
 * 
 * @author toby
 */
public class CourseTab {

    private String id;
    private String name;
    private String size;
    private List<Course> courses;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public CourseTab() {
        super();
    }

    public CourseTab(String id, String name) {
        super();
        this.id = StringUtils.trimToEmpty(id);
        this.name = StringUtils.trimToEmpty(name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
