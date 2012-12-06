package com.dc.model;

public class CourseFile {

    public CourseFile() {
        super();
    }

    public CourseFile(String givenType, String givenSrc, String givenSynctime) {
        super();
        this.type = givenType;
        this.src = givenSrc;
        this.synctime = givenSynctime;
    }

    private String type;
    private String src;
    private String synctime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSynctime() {
        return synctime;
    }

    public void setSynctime(String synctime) {
        this.synctime = synctime;
    }
}
