package com.dc.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class RxResponseResolve {

    public Map<String, Object> putErrorMsg(Map<String, Object> model, String errorTip) {
        model.put("errInfo", errorTip);
        return model;
    }

    public boolean resolveLogin(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            String status = responseFile.get(1).substring(0, 1);
            if ("1".equals(status)) {
                return true;
            }

        }
        return false;
    }

    public boolean resolveOpenTable(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(1).startsWith("开台成功")) {
                return true;
            }
        }
        return false;
    }

    public boolean resolveCloseTable(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(2).startsWith("撤台成功")) {
                return true;
            }
        }
        return false;
    }
}
