package com.dc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dc.model.Course;
import com.dc.model.CoursePackage;
import com.dc.model.IpadRequestInfo;

@Service
public class CacheService {

    private static Map<String, IpadRequestInfo> cacheIpadInfo;

    private static Map<String, Course> cacheCourseMap;

    private static Map<String, CoursePackage> cacheCoursePackageMap;

    private static Map<String, Course> cacheCourseMapKeyName;

    /**
     * 存储tableid->sid用于换台
     */
    private static Map<String, String> cacheTableIdAndSidMap;

    public static String getSid(String tableId) {
        if (cacheTableIdAndSidMap != null) {
            return cacheTableIdAndSidMap.get(tableId.trim());
        }
        else {
            return null;
        }
    }

    public static void clearSid(String sid) {
        if (cacheIpadInfo != null) {
            cacheIpadInfo.remove(sid);
        }
    }

    public static void saveSid(String tableId, String sid) {
        System.err.println("put table id: " + tableId + " value sid: " + sid);
        if (cacheTableIdAndSidMap == null) {
            cacheTableIdAndSidMap = new HashMap<String, String>();
        }
        cacheTableIdAndSidMap.put(tableId.trim(), sid.trim());
    }

    public static Course getCourse(String courseNo) {
        if (cacheCourseMap != null) {
            return cacheCourseMap.get(courseNo);
        }
        else {
            return null;
        }
    }

    public static Course getCourseByName(String courseName) {
        if (cacheCourseMapKeyName != null) {
            return cacheCourseMapKeyName.get(courseName);
        }
        else {
            return null;
        }
    }

    public static void saveCourse(List<Course> courseList) {
        cacheCourseMap = new HashMap<String, Course>();
        cacheCourseMapKeyName = new HashMap<String, Course>();
        for (Course c : courseList) {
            cacheCourseMap.put(c.getCourseNo(), c);
            cacheCourseMapKeyName.put(c.getCourseName().trim(), c);
        }
    }

    public static CoursePackage getCoursePackage(String id) {
        if (cacheCoursePackageMap != null) {
            return cacheCoursePackageMap.get(id);
        }
        else {
            return null;
        }
    }

    public static void saveCoursePackage(List<CoursePackage> coursePackages) {
        cacheCoursePackageMap = new HashMap<String, CoursePackage>();
        for (CoursePackage c : coursePackages) {
            cacheCoursePackageMap.put(c.getId(), c);
        }
    }

    public static IpadRequestInfo getIpadInfo(String sid) {
        if (cacheIpadInfo != null) {
            return cacheIpadInfo.get(sid);
        }
        else {
            return null;
        }
    }

    public static void putIpadRequestInfo(String sid, IpadRequestInfo requestInfo) {
        if (cacheIpadInfo == null) {
            cacheIpadInfo = new HashMap<String, IpadRequestInfo>();
        }
        cacheIpadInfo.put(sid, requestInfo);
    }

}
