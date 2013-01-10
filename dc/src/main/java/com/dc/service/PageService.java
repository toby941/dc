package com.dc.service;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dc.constants.Constants;
import com.dc.model.Course;
import com.dc.model.CourseFile;
import com.dc.model.CoursePackage;
import com.dc.model.CourseTab;

@Service
public class PageService {

    private static String desc_http_templete = "/desc/{0}.html";

    private static String photo_templete = "/photo/{0}/{1}";

    @Autowired
    private CacheService cacheService;

    private String descFloderPath;
    private String photoFloderPath;

    private String packageDescFloderPath;
    private String packagePhotoFloderPath;

    public String getPackageDescFloderPath() {
        return packageDescFloderPath;
    }

    public void setPackageDescFloderPath(String packageDescFloderPath) {
        this.packageDescFloderPath = packageDescFloderPath;
    }

    public String getPackagePhotoFloderPath() {
        return packagePhotoFloderPath;
    }

    public void setPackagePhotoFloderPath(String packagePhotoFloderPath) {
        this.packagePhotoFloderPath = packagePhotoFloderPath;
    }

    private String host;

    @Autowired
    private RxResponseResolve resolve;

    public List<Course> getAllCourse() throws IOException {

        List<CourseTab> courseTabs = resolve.resolveGetMenuList();

        List<Course> courses = new ArrayList<Course>();
        for (CourseTab tab : courseTabs) {
            List<Course> subCourses = tab.getCourses();
            for (Course subCourse : subCourses) {
                subCourse.setCourseTypeStr(tab.getName());
                courses.add(subCourse);
            }
        }
        cacheService.saveCourse(courses);
        return courses;
    }

    /**
     * 获取套餐列表
     * 
     * @return
     * @throws IOException
     */
    public List<CoursePackage> getAllCoursePackage() throws IOException {

        List<CoursePackage> coursePackages = resolve.resolveGetMenuPackageList();
        cacheService.saveCoursePackage(coursePackages);
        return coursePackages;
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public CoursePackage getPackage(String id) {
        return cacheService.getCoursePackage(id);
    }

    public Course getCourse(String courseNo) {
        return cacheService.getCourse(courseNo);
    }

    /**
     * 按类型获取文件存取目录
     * 
     * @param type
     * @param isDesc 是否描述
     * @return
     */
    public String getPath(String type, boolean isDesc) {
        if (Constants.page_type_course.equals(type)) {
            if (isDesc) {
                return descFloderPath;
            }
            else {
                return photoFloderPath;
            }
        }
        else if (Constants.page_type_package.equals(type)) {
            if (isDesc) {
                return packageDescFloderPath;
            }
            else {
                return packagePhotoFloderPath;
            }
        }
        return null;
    }

    /**
     * 获取描述
     * 
     * @param courseNo
     * @return
     * @throws IOException
     */
    public String getDesc(String id, String type) throws IOException {
        String descPath = getPath(type, true);
        File f = new File(descPath + id + ".txt");
        if (f.exists()) {
            return FileUtils.readFileToString(f, "UTF-8");
        }
        else {
            return StringUtils.EMPTY;
        }
    }

    public String getDescFloderPath() {
        return descFloderPath;
    }

    public String getDescHttpSrc(String courseNo) {
        return MessageFormat.format(desc_http_templete, courseNo);
    }

    public List<CourseFile> getFileNode(String courseNo) throws IOException {
        File folder = new File(photoFloderPath + courseNo);
        List<CourseFile> fileList = new ArrayList<CourseFile>();
        if (folder.exists() && folder.list().length > 0) {
            File[] photoFiles = folder.listFiles();
            for (File f : photoFiles) {
                String name = f.getName();
                String time = String.valueOf(f.lastModified());
                String src = MessageFormat.format(photo_templete, courseNo, name);
                CourseFile file = new CourseFile("jpg", src, time);
                fileList.add(file);
            }
        }
        File descFile = new File(descFloderPath + courseNo + ".txt");
        if (descFile.exists()) {
            CourseFile descCourseFile =
                    new CourseFile("html", getDescHttpSrc(courseNo), String.valueOf(descFile.lastModified()));
            fileList.add(descCourseFile);
        }
        return fileList;
    }

    public String getHost() {
        return host;
    }

    /**
     * 获取图片流
     * 
     * @param courseNo
     * @return
     * @throws IOException
     */
    public byte[] getPhoto(String id, String index, String type) throws IOException {
        String path = getPath(type, false);
        File f = new File(path + id + "/" + index + ".jpg");
        if (f.exists()) {
            return FileUtils.readFileToByteArray(f);
        }
        else {
            return null;
        }
    }

    public String getPhotoFloderPath() {
        return photoFloderPath;
    }

    public RxResponseResolve getResolve() {
        return resolve;
    }

    public void saveDesc(String id, String desc, String type) throws IOException {
        String path = getPath(type, true);
        File f = new File(path + id + ".txt");
        FileUtils.writeStringToFile(f, desc, "UTF-8");
    }

    public void savePhoto(MultipartFile fileToUpload, String id, String index, String type) throws IOException {
        String path = getPath(type, false);

        File floder = new File(path + id);
        if (!floder.exists()) {
            FileUtils.forceMkdir(floder);
        }
        byte[] content = fileToUpload.getBytes();
        if (content != null && content.length > 0) {
            File f = new File(path + id + "/" + index + ".jpg");
            if (f.exists()) {
                FileUtils.forceDelete(f);
            }
            f = new File(path + id + "/" + index + ".jpg");
            FileUtils.writeByteArrayToFile(f, content);
        }
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public void setDescFloderPath(String descFloderPath) {
        this.descFloderPath = descFloderPath;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPhotoFloderPath(String photoFloderPath) {
        this.photoFloderPath = photoFloderPath;
    }

    public void setResolve(RxResponseResolve resolve) {
        this.resolve = resolve;
    }
}
