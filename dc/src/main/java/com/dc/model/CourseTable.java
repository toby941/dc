package com.dc.model;

/**
 * 桌台
 * 
 * @author toby
 */
public class CourseTable {

    private String id;
    private String name;

    public CourseTable() {
        super();
    }

    public CourseTable(String gid, String gname) {
        super();
        this.id = gid;
        this.name = gname;
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

}
