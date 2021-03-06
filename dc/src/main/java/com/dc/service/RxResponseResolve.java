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

import com.dc.constants.Constants;
import com.dc.model.Course;
import com.dc.model.CourseFile;
import com.dc.model.CoursePackage;
import com.dc.model.CoursePackageItem;
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

    /**
     * 解析RX入口
     * 
     * @param responseFile
     * @param model
     * @param errorTip
     * @param requestXml
     * @return
     */
    public Map<String, Object> resolve(List<String> responseFile, Map<String, Object> model, String errorTip, RequestXml requestXml) {
        boolean resolveResult = false;
        String sid = null;
        List<IpadRequestInfo> resolveList = new ArrayList<IpadRequestInfo>();
        List<CourseTab> courseTabs = new ArrayList<CourseTab>();
        List<CourseFile> courseFiles = new ArrayList<CourseFile>();
        List<Course> courseLists = null;
        List<CourseTable> courseTables = new ArrayList<CourseTable>();
        List<CoursePackage> coursePackages = new ArrayList<CoursePackage>();
        Class c = this.getClass();
        Method[] ms = c.getDeclaredMethods();
        for (Method method : ms) {
            if (method.getName().contains(requestXml.getAction())) {
                Type t = method.getGenericReturnType();
                log.error("handle " + t.toString() + " " + method.getName());
                if ("java.util.List<com.dc.model.IpadRequestInfo>".equals(t.toString())) {
                    resolveList = (List<IpadRequestInfo>) ReflectionUtils.invokeMethod(method, this, responseFile);
                } else if ("java.util.List<com.dc.model.CourseTab>".equals(t.toString())) {
                    courseTabs = (List<CourseTab>) ReflectionUtils.invokeMethod(method, this);
                } else if ("java.util.List<com.dc.model.CourseFile>".equals(t.toString())) {
                    courseFiles = (List<CourseFile>) ReflectionUtils.invokeMethod(method, this);
                } else if ("java.util.List<com.dc.model.Course>".equals(t.toString())) {
                    courseLists = (List<Course>) ReflectionUtils.invokeMethod(method, this, responseFile);
                } else if ("java.util.List<com.dc.model.CourseTable>".equals(t.toString())) {
                    courseTables = (List<CourseTable>) ReflectionUtils.invokeMethod(method, this);
                } else if ("java.util.List<com.dc.model.CoursePackage>".equals(t.toString())) {
                    coursePackages = (List<CoursePackage>) ReflectionUtils.invokeMethod(method, this);
                } else if ("class java.lang.String".equals(t.toString())) {
                    sid = (String) ReflectionUtils.invokeMethod(method, this, requestXml.getParamValue("TableId"));
                } else {
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
        model.put("coursePackages", coursePackages);
        model.put("sid", sid);
        if (sid == null && !resolveResult && resolveList.size() == 0 && CollectionUtils.isEmpty(coursePackages) && CollectionUtils.isEmpty(courseFiles)
                && CollectionUtils.isEmpty(courseTabs) && CollectionUtils.isEmpty(courseTables) && courseLists == null) {
            String responesError = getErrInLine(responseFile);
            if (responesError != null && responesError.length() > 0) {
                model = putErrorMsg(model, responesError);
            } else {
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
        Pattern coursePackageItemPattern = Pattern.compile("(\\d{2})(\\d{5})\\s*(\\S*)\\s*(\\S*).*");
        Pattern coursePackagePattern = Pattern.compile("(\\d{2})(.{0,20}).*");
        String s1 = "0101001        1     6.00份  ";
        String s2 = "01new                 ";
        System.out.println(coursePackageItemPattern.matcher(s1).matches());
        System.out.println(coursePackagePattern.matcher(s2).matches());
    }

    /**
     * 获取套餐内容项 不请求TX 本地处理
     * 
     * @throws IOException
     */
    public List<CoursePackage> resolveGetMenuPackageList() throws IOException {
        File couresPackageFile = new File(PathUtils.coursePackageFilepath);
        File coursePackageContentFile = new File(PathUtils.coursePackageContentFilePath);
        // 菜品套餐内容表
        // 套餐编号（2位）菜品编号（5位）数量（9位）单价（9位）单位（4位）缺省选中标志（1位）套餐菜组号（2位）
        // 0101001 1 6.00份

        // 菜品套餐表
        // 套餐编号（2位）套餐名称（20位）
        // 01new
        Pattern coursePackageItemPattern = Pattern.compile("(\\d{2})(\\d{5})\\s*(\\S*)\\s*(\\S*).*");
        Pattern coursePackagePattern = Pattern.compile("(\\d{2})(.{0,20}).*");

        List<String> couresPackageFiles = FileUtils.readLines(couresPackageFile, "GBK");
        List<String> coursePackageContentFiles = FileUtils.readLines(coursePackageContentFile, "GBK");

        List<CoursePackageItem> coursePackageItems = new ArrayList<CoursePackageItem>();
        List<CoursePackage> coursePackages = new ArrayList<CoursePackage>();

        for (int i = 0; i < coursePackageContentFiles.size(); i++) {
            String responseStr = coursePackageContentFiles.get(i);
            Matcher m = coursePackageItemPattern.matcher(responseStr);
            if (m.matches() && (m.groupCount() == 4)) {
                CoursePackageItem c = new CoursePackageItem(m.group(1), m.group(2), m.group(3), m.group(4));
                List<CourseFile> fileList = pageService.getFileNode(c.getCourseNo(), Constants.page_type_course);
                c.setFiles(fileList);
                Course course = pageService.getCourse(c.getCourseNo());
                if (course != null) {
                    c.setCourseName(course.getCourseName());
                }
                coursePackageItems.add(c);
            }
        }

        for (int i = 0; i < couresPackageFiles.size(); i++) {
            String responseStr = couresPackageFiles.get(i);
            Matcher m = coursePackagePattern.matcher(responseStr);
            if (m.matches() && (m.groupCount() == 2)) {
                CoursePackage cp = new CoursePackage(m.group(1), m.group(2));
                List<CourseFile> files = pageService.getFileNode(m.group(1), Constants.page_type_package);
                cp.setFiles(files);
                coursePackages.add(cp);
            }
        }

        for (CoursePackage cp : coursePackages) {
            String id = cp.getId();
            List<CoursePackageItem> subCoursePackageItems = new ArrayList<CoursePackageItem>();
            for (CoursePackageItem c : coursePackageItems) {
                if (c.getId().equals(id)) {
                    subCoursePackageItems.add(c);
                }
            }
            cp.setCoursePackageItems(subCoursePackageItems);
        }

        return coursePackages;
    }

    /**
     * 获取菜单 不请求TX 本地处理
     * 
     * @param responseFile
     * @return
     * @throws IOException
     */

    public List<CourseTab> resolveGetMenuList() throws IOException {
        File courseFile = new File(PathUtils.courseFilePath);
        File courseTabFile = new File(PathUtils.courseTabPath);
        // 0100101凉拌黄瓜 6.00份 份 0 LBHG
        // 菜品编号(5)类别号(2)中文名称(20)单价(9)单位(4)重量单位(4)需要确认重量否(1)制作要求(45)拼音编码(10)
        // 菜品类别(2)类别名称(20)
        // 01凉菜
        // 套餐列表
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
                Course c = new Course(m.group(1), m.group(2), m.group(3), m.group(4), m.group(5), m.group(6), m.group(7));
                List<CourseFile> fileList = pageService.getFileNode(c.getCourseNo(), Constants.page_type_course);
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

    public boolean resolveDishChecked(List<String> responseFile) {
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(1).startsWith("划单成功")) {
                return true;
            }
        }
        return false;
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

        List<CoursePackage> packageList = pageService.getAllCoursePackage();
        for (CoursePackage cp : packageList) {
            List<CourseFile> subCourseFiles = cp.getFiles();
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
     * @return null 解析失败 empty尚未点单
     * @throws IOException
     */
    public List<Course> resolveGetOrderList(List<String> responseFile) throws IOException {
        boolean isDone = false;
        if (CollectionUtils.isNotEmpty(responseFile) && responseFile.size() >= 2) {
            if (responseFile.get(1).contains(("成功"))) {
                isDone = true;
            }
        }
        if (!isDone) {
            return null;
        }

        List<Course> courseList = new ArrayList<Course>();
        try {
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
        } catch (Exception e) {
            log.error("resolveGetOrderList error", e);
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
