package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.dc.model.IpadRequestInfo;
import com.dc.utils.PathUtils;
import com.dc.web.controller.RequestXml;
import com.dc.web.socket.SocketClient;

@Service
public class RxResponseResolve {

    private static final Logger log = Logger.getLogger(SocketClient.class);

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
        List<IpadRequestInfo> resolveList = new ArrayList<IpadRequestInfo>();
        Class c = this.getClass();
        Method[] ms = c.getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().contains(requestXml.getAction())) {
                Type t = method.getGenericReturnType();
                log.error("type: " + t.toString());
                if ("java.util.List<com.dc.model.IpadRequestInfo>".equals(t.toString())) {
                    log.error("handle " + t.toString() + " " + method.getName());
                    resolveList = (List<IpadRequestInfo>) ReflectionUtils.invokeMethod(method, this, responseFile);
                }
                else {
                    resolveResult = (Boolean) ReflectionUtils.invokeMethod(method, this, responseFile);
                }
                break;
            }
        }

        model.put("ipadList", resolveList);
        model.put("size", resolveList.size());
        if (!resolveResult && resolveList.size() == 0) {
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

    public List<IpadRequestInfo> resolveGetTables(List<String> responseFile) {
        List<IpadRequestInfo> list = new ArrayList<IpadRequestInfo>();
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            log.error("resolveGetTables: " + responseFile.get(1));
            if (responseFile.get(1).startsWith("查询空闲成功")) {
                Pattern p = Pattern.compile("(\\D*)(\\d*)\\s*(\\d*)");
                for (int i = 2; i < responseFile.size(); i++) {
                    String responseStr = responseFile.get(i);
                    Matcher m = p.matcher(responseStr);
                    if (m.matches() && m.groupCount() == 3) {
                        IpadRequestInfo requestInfo = new IpadRequestInfo(m.group(2), m.group(3));
                        list.add(requestInfo);
                    }
                }
            }
        }
        return list;
    }

    // 一楼\\新建房间001 6 一楼\\新建房间031 12
    public static void main(String[] args) throws IOException {
        List<String> files = FileUtils.readLines(new File("C:\\tmp\\R1.TXT"), "GBK");
        for (int i = 2; i < files.size(); i++) {
            String responseStr = files.get(i);
            Pattern p = Pattern.compile("(\\D*)(\\d*)\\s*(\\d*)");
            Matcher m = p.matcher(responseStr);
            if (m.matches()) {
                System.out.println(m.groupCount());
                for (int j = 1; j <= m.groupCount(); j++) {
                    System.out.print(m.group(j));
                }
                System.out.println("");
            }
        }
    }

    /**
     * 不请求TX 本地处理
     * 
     * @param responseFile
     * @return
     */
    // 0100101凉拌黄瓜 6.00份 份 0 LBHG
    // 菜品编号(5)类别号(2)中文名称(20)单价(9)单位(4)重量单位(4)需要确认重量否(1)制作要求(45)拼音编码(10)
    public boolean resolveGetMenuList(List<String> responseFile) {
        File f = new File(PathUtils.menuFilePath);
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
