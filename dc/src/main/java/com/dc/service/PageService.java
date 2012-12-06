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

import com.dc.model.Course;
import com.dc.model.CourseTab;

@Service
public class PageService {

    @Autowired
    private RxResponseResolve resolve;

    @Autowired
    private CacheService cacheService;

    private String photoFloderPath;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    private String descFloderPath;
    private String host;

    private static String photo_templete = "{0}/photo/{1}/{2}";
    private static String desc_templete = "{0}/desc/{1}.html";

    public List<String> getPhotoURL(String courseNo) {
        File folder = new File(photoFloderPath + courseNo);
        List<String> filePathList = new ArrayList<String>();
        if (folder.exists() && folder.list().length > 0) {
            String[] names = folder.list();
            for (String name : names) {
                filePathList.add(MessageFormat.format(photo_templete, host, courseNo, name));
            }
        }
        return filePathList;
    }

    /**
     * 获取图片流
     * 
     * @param courseNo
     * @return
     * @throws IOException
     */
    public byte[] getPhoto(String courseNo, String index) throws IOException {
        File f = new File(photoFloderPath + courseNo + "/" + index + ".jpg");
        if (f.exists()) {
            return FileUtils.readFileToByteArray(f);
        } else {
            return null;
        }
    }

    /**
     * 获取描述
     * 
     * @param courseNo
     * @return
     * @throws IOException
     */
    public String getDesc(String courseNo) throws IOException {
        File f = new File(descFloderPath + courseNo + ".txt");
        if (f.exists()) {
            return FileUtils.readFileToString(f);
        } else {
            return StringUtils.EMPTY;
        }
    }

    public RxResponseResolve getResolve() {
        return resolve;
    }

    public void setResolve(RxResponseResolve resolve) {
        this.resolve = resolve;
    }

    public CacheService getCacheService() {
        return cacheService;
    }

    public void setCacheService(CacheService cacheService) {
        this.cacheService = cacheService;
    }

    public String getPhotoFloderPath() {
        return photoFloderPath;
    }

    public void setPhotoFloderPath(String photoFloderPath) {
        this.photoFloderPath = photoFloderPath;
    }

    public String getDescFloderPath() {
        return descFloderPath;
    }

    public void setDescFloderPath(String descFloderPath) {
        this.descFloderPath = descFloderPath;
    }

    public void saveDesc(String courseNo, String desc) throws IOException {
        File f = new File(descFloderPath + courseNo + ".txt");
        FileUtils.writeStringToFile(f, desc, "UTF-8");
    }

    public void savePhoto(MultipartFile fileToUpload, String photoName, String index) throws IOException {
        File floder = new File(photoFloderPath + photoName);
        if (!floder.exists()) {
            FileUtils.forceMkdir(floder);
        }
        byte[] content = fileToUpload.getBytes();
        if (content != null && content.length > 0) {
            String name = fileToUpload.getName();
            File f = new File(photoFloderPath + photoName + "/" + index + ".jpg");
            if (f.exists()) {
                FileUtils.forceDelete(f);
            }
            f = new File(photoFloderPath + photoName + "/" + index + ".jpg");
            FileUtils.writeByteArrayToFile(f, content);
        }
    }

    public Course getCourse(String courseNo) {
        return cacheService.getCourse(courseNo);
    }

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

    public String getDescHttpSrc(String courseNo) {
        return MessageFormat.format(desc_templete, host, courseNo);
    }
}
