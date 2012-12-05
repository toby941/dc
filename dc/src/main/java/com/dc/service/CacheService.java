package com.dc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dc.model.Course;
import com.dc.model.IpadRequestInfo;

@Service
public class CacheService {

    private static Map<String, IpadRequestInfo> cacheIpadInfo;

    private static Map<String, Course> cacheCourseMap;

    public static Course getCourse(String courseNo) {
        if (cacheCourseMap != null) {
            return cacheCourseMap.get(courseNo);
        }
        else {
            return null;
        }
    }

    public static void saveCourse(List<Course> courseList) {
        cacheCourseMap = new HashMap<String, Course>();
        for (Course c : courseList) {
            cacheCourseMap.put(c.getCourseNo(), c);
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
