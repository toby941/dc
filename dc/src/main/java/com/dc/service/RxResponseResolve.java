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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.dc.model.Course;
import com.dc.model.CourseTab;
import com.dc.model.IpadRequestInfo;
import com.dc.utils.PathUtils;
import com.dc.web.controller.RequestXml;
import com.dc.web.socket.SocketClient;

@Service
public class RxResponseResolve {

    private static final Logger log = Logger.getLogger(SocketClient.class);

    @Autowired
    private PageService pageService;

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
        List<CourseTab> courseTabs = new ArrayList<CourseTab>();
        Class c = this.getClass();
        Method[] ms = c.getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().contains(requestXml.getAction())) {
                Type t = method.getGenericReturnType();
                log.error("handle " + t.toString() + " " + method.getName());
                if ("java.util.List<com.dc.model.IpadRequestInfo>".equals(t.toString())) {
                    resolveList = (List<IpadRequestInfo>) ReflectionUtils.invokeMethod(method, this, responseFile);
                }
                else if ("java.util.List<com.dc.model.CourseTab>".equals(t.toString())) {
                    courseTabs = (List<CourseTab>) ReflectionUtils.invokeMethod(method, this);
                }
                else {
                    resolveResult = (Boolean) ReflectionUtils.invokeMethod(method, this, responseFile);
                }
                break;
            }
        }

        model.put("ipadList", resolveList);
        model.put("size", resolveList.size());
        model.put("courseTabs", courseTabs);
        model.put("courseTabSize", courseTabs.size());
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
        List<String> files = FileUtils.readLines(new File("D:\\Dropbox\\doc\\my\\点餐\\protocols\\txt\\菜品表.TXT"), "GBK");
        for (int i = 2; i < files.size(); i++) {
            String responseStr = files.get(i);
            Pattern p = Pattern.compile("(\\d{5})(\\d{2})(\\S*)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*).*");
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
     * @throws IOException
     */
    // 0100101凉拌黄瓜 6.00份 份 0 LBHG
    // 菜品编号(5)类别号(2)中文名称(20)单价(9)单位(4)重量单位(4)需要确认重量否(1)制作要求(45)拼音编码(10)
    // 菜品类别(2)类别名称(20)
    // 01凉菜

    public List<CourseTab> resolveGetMenuList() throws IOException {
        File courseFile = new File(PathUtils.courseFilePath);
        File courseTabFile = new File(PathUtils.courseTabPath);

        Pattern coursePattern = Pattern.compile("(\\d{5})(\\d{2})(\\S*)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*)\\s*(\\S*).*");
        Pattern courseTabPattern = Pattern.compile("(\\d*)(\\D*)");

        List<String> courseFiles = FileUtils.readLines(courseFile, "GBK");
        List<String> courseTabFiles = FileUtils.readLines(courseTabFile, "GBK");

        List<Course> courses = new ArrayList<Course>();
        List<CourseTab> courseTabs = new ArrayList<CourseTab>();

        for (int i = 0; i < courseFiles.size(); i++) {
            String responseStr = courseFiles.get(i);
            Matcher m = coursePattern.matcher(responseStr);
            if (m.matches() && (m.groupCount() == 7)) {
                Course c =
                        new Course(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7));
                String desc = pageService.getDesc(c.getCourseNo());
                c.setDesc(desc);
                courses.add(c);
            }
        }

        for (int i = 0; i < courseTabFiles.size(); i++) {
            String responseStr = courseTabFiles.get(i);
            Matcher m = courseTabPattern.matcher(responseStr);
            if (m.matches() && (m.groupCount() == 2)) {
                CourseTab tab = new CourseTab(m.group(1), m.group(2));
                courseTabs.add(tab);
            }
        }

        for (CourseTab tab : courseTabs) {
            String id = tab.getId();
            List<Course> subCosCourses = new ArrayList<Course>();
            for (Course c : courses) {
                if (c.getCourseType().equals(id)) {
                    subCosCourses.add(c);
                }
            }
            tab.setCourses(subCosCourses);
            tab.setSize(String.valueOf(subCosCourses.size()));
        }

        return courseTabs;
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
