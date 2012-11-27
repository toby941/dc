package com.dc.service;

import java.util.HashMap;
import java.util.Map;

import com.dc.model.IpadRequestInfo;

public class CacheService {

    private static Map<String, IpadRequestInfo> cacheIpadInfo;

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
