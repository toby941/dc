package com.dc.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.dc.model.IpadRequestInfo;
import com.dc.web.controller.RequestXml;

@Service
public class RxResponseResolve {

    /**
     * 解析RX入口
     * 
     * @param responseFile
     * @param model
     * @param errorTip
     * @param requestXml
     * @return
     */
    public Map<String, Object> resolve(List<String> responseFile, Map<String, Object> model, String errorTip,
            RequestXml requestXml) {
        boolean resolveResult = false;

        Class c = this.getClass();
        Method[] ms = c.getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().contains(requestXml.getAction())) {
                resolveResult = (boolean) ReflectionUtils.invokeMethod(method, this, responseFile);
                break;
            }
        }

        if (!resolveResult) {
            model = putErrorMsg(model, errorTip);
        }
        return model;
    }

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
            if (responseFile.get(1).startsWith("撤台成功")) {
                return true;
            }
        }
        return false;
    }

    public boolean resolveGetTables(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(1).startsWith("查询空闲成功")) {

                List<IpadRequestInfo> list = new ArrayList<IpadRequestInfo>();
                for (int i = 2; i < responseFile.size(); i++) {
                    String responseStr = responseFile.get(i);
                    String[] info = responseStr.split(" ");
                    if (info.length == 3) {
                        IpadRequestInfo requestInfo = new IpadRequestInfo(info[1], info[2]);
                        list.add(requestInfo);
                    }
                }

                return true;
            }

        }
        return false;
    }

    /**
     * 不请求TX 本地处理
     * 
     * @param responseFile
     * @return
     */
    public boolean resolveGetMenuList(List<String> responseFile) {
        return false;
    }

    public boolean resolveOrderMenu(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(1).startsWith("点菜成功")) {
                return true;
            }
        }
        return false;
    }

    public boolean resolveCheckout(List<String> responseFile) {
        return true;
    }

    public boolean resolveGetSyncFileList(List<String> responseFile) {
        return false;
    }

}
