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

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.dc.model.Course;
import com.dc.model.CourseFile;
import com.dc.model.CourseTab;
import com.dc.model.CourseTable;
import com.dc.model.IpadRequestInfo;
import com.dc.utils.PathUtils;
import com.dc.web.controller.RequestXml;
import com.dc.web.socket.SocketClient;

@Service
public class RxResponseResolve {

    private static final Logger log = Logger.getLogger(SocketClient.class);

    @Autowired
    private PageService pageService;

    @PostConstruct
    private void init() {
        try {
            resolveGetSyncFileList();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

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
        String sid = null;
        List<IpadRequestInfo> resolveList = new ArrayList<IpadRequestInfo>();
        List<CourseTab> courseTabs = new ArrayList<CourseTab>();
        List<CourseFile> courseFiles = new ArrayList<CourseFile>();
        List<Course> courseLists = new ArrayList<Course>();
        List<CourseTable> courseTables = new ArrayList<CourseTable>();
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
                else if ("java.util.List<com.dc.model.CourseFile>".equals(t.toString())) {
                    courseFiles = (List<CourseFile>) ReflectionUtils.invokeMethod(method, this);
                }
                else if ("java.util.List<com.dc.model.Course>".equals(t.toString())) {
                    courseLists = (List<Course>) ReflectionUtils.invokeMethod(method, this, responseFile);
                }
                else if ("java.util.List<com.dc.model.CourseTable>".equals(t.toString())) {
                    courseTables = (List<CourseTable>) ReflectionUtils.invokeMethod(method, this);
                }
                else if ("class java.lang.String".equals(t.toString())) {
                    sid = (String) ReflectionUtils.invokeMethod(method, this, requestXml.getParamValue("TableId"));
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
        model.put("courseFiles", courseFiles);
        model.put("courseLists", courseLists);
        model.put("courseTables", courseTables);
        model.put("courseTabSize", courseTabs.size());
        model.put("sid", sid);
        if (sid == null && !resolveResult && resolveList.size() == 0 && CollectionUtils.isEmpty(courseFiles)
                && CollectionUtils.isEmpty(courseTabs) && CollectionUtils.isEmpty(courseTables)
                && CollectionUtils.isEmpty(courseLists)) {
            String responesError = getErrInLine(responseFile);
            if (responesError != null && responesError.length() > 0) {
                model = putErrorMsg(model, responesError);
            }
            else {
                model = putErrorMsg(model, errorTip);
            }
        }
        return model;
    }

    /**
     * 将错误信息转换为1行返回
     * 
     * @param responseFile
     * @return
     */
    private String getErrInLine(List<String> responseFile) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() > 1) {
            for (int i = 1; i < responseFile.size(); i++) {
                sb.append(responseFile.get(i)).append(" ");
            }
        }
        return sb.toString().trim();
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

    public String resolveSwitchTable(String tableId) {
        String sid = CacheService.getSid(tableId);
        return sid;
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
        Pattern p = Pattern.compile("(\\S+)\\s+(\\d+)\\s+(\\S+)\\s+");
        String s = "白兰地         1 份 ";
        System.out.println(p.matcher(s).matches());
        System.out.println(p.matcher(s).groupCount());
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
                List<CourseFile> fileList = pageService.getFileNode(c.getCourseNo());
                c.setFiles(fileList);
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

    public List<CourseFile> resolveGetSyncFileList() throws IOException {
        List<Course> courseList = pageService.getAllCourse();
        List<CourseFile> courseFiles = new ArrayList<CourseFile>();
        for (Course c : courseList) {
            List<CourseFile> subCourseFiles = c.getFiles();
            if (CollectionUtils.isNotEmpty(subCourseFiles)) {
                courseFiles.addAll(subCourseFiles);
            }
        }
        return courseFiles;
    }

    /**
     * 解析上位软件返回的已点菜单
     * 
     * @param responseFile
     * @return
     * @throws IOException
     */
    public List<Course> resolveGetOrderList(List<String> responseFile) throws IOException {
        List<Course> courseList = new ArrayList<Course>();
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            Pattern coursePattern = Pattern.compile("(\\d+)\\D+(\\d+)=(\\d+)");
            for (int i = 2; i < responseFile.size(); i = i + 2) {
                String name = responseFile.get(i).replace("⊙", "").trim();
                String secondLine = responseFile.get(i + 1).trim();
                Matcher m = coursePattern.matcher(secondLine);
                if (m.matches() && m.groupCount() == 3) {
                    Course c = new Course(name, m.group(1), m.group(2), m.group(3));
                    courseList.add(c);
                }
            }
        }
        if (courseList.isEmpty()) {
            Pattern coursePattern = Pattern.compile("(\\S+)\\s+(\\d+)\\s+(\\S+)\\s*");
            for (int i = 2; i < responseFile.size(); i++) {
                String line = responseFile.get(i);
                Matcher m = coursePattern.matcher(line);
                if (m.matches() && m.groupCount() == 3) {
                    Course c = new Course(m.group(1), m.group(2), "", "");
                    if (m.group(3).toLowerCase().contains("v")) {
                        c.setStatus("1");
                    }
                    courseList.add(c);
                }
            }
        }

        for (Course c : courseList) {
            String name = c.getCourseName().trim();
            Course cacheCourse = CacheService.getCourseByName(name);
            if (cacheCourse != null) {
                c.setCourseNo(cacheCourse.getCourseNo());
                c.setCoursePrice(cacheCourse.getCoursePrice());
                c.setCourseUnit(cacheCourse.getCourseUnit());

            }
        }

        return courseList;
    }

    /**
     * 读本地文件 获取所有桌台信息
     * 
     * @return
     * @throws IOException
     */
    public List<CourseTable> resolveGetAllTables() throws IOException {
        File courseTableFile = new File(PathUtils.courseTablePath);
        Pattern courseTablePattern = Pattern.compile("(\\d+)(.+)");
        List<String> courseTableFiles = FileUtils.readLines(courseTableFile, "GBK");

        List<CourseTable> courseTables = new ArrayList<CourseTable>();

        for (int i = 0; i < courseTableFiles.size(); i++) {
            String responseStr = courseTableFiles.get(i);
            Matcher m = courseTablePattern.matcher(responseStr);
            if (m.matches() && (m.groupCount() == 2)) {
                CourseTable c = new CourseTable(m.group(1), m.group(2));
                courseTables.add(c);
            }
        }
        return courseTables;
    }
}
