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

    @Autowired
    private CacheService cacheService;

    private String descFloderPath;
    private String photoFloderPath;

    // 临时文件path
    private String tmpPhotoFloderPath;

    public String getTmpPhotoFloderPath() {
        return tmpPhotoFloderPath;
    }

    public void setTmpPhotoFloderPath(String tmpPhotoFloderPath) {
        this.tmpPhotoFloderPath = tmpPhotoFloderPath;
    }

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
            } else {
                return photoFloderPath;
            }
        } else if (Constants.page_type_package.equals(type)) {
            if (isDesc) {
                return packageDescFloderPath;
            } else {
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
        } else {
            return StringUtils.EMPTY;
        }
    }

    public String getDescFloderPath() {
        return descFloderPath;
    }

    private static String desc_course_http_templete = "/desc/{0}.htm";
    private static String photo_course_templete = "/photo/{0}/{1}";

    private static String desc_package_http_templete = "/packagedesc/{0}.htm";
    private static String photo_package_templete = "/packagephoto/{0}/{1}";

    public List<CourseFile> getFileNode(String courseNo, String type) throws IOException {
        String photoPath = null;
        String descPath = null;
        String photoTemplete = null;
        String descTemplete = null;
        if (Constants.page_type_course.equals(type)) {
            photoPath = photoFloderPath;
            descPath = descFloderPath;
            photoTemplete = photo_course_templete;
            descTemplete = desc_course_http_templete;
        } else {
            photoPath = packagePhotoFloderPath;
            descPath = packageDescFloderPath;
            photoTemplete = photo_package_templete;
            descTemplete = desc_package_http_templete;
        }

        File folder = new File(photoPath + courseNo);
        List<CourseFile> fileList = new ArrayList<CourseFile>();
        if (folder.exists() && folder.list().length > 0) {
            File[] photoFiles = folder.listFiles();
            for (File f : photoFiles) {
                if (f.getName().toLowerCase().endsWith("jpg")) {
                    String name = f.getName();
                    String time = String.valueOf(f.lastModified());
                    String src = MessageFormat.format(photoTemplete, courseNo, name);
                    CourseFile file = new CourseFile("jpg", src, time);
                    fileList.add(file);
                }
            }
        }
        File descFile = new File(descPath + courseNo + ".txt");
        if (descFile.exists()) {
            String time = String.valueOf(descFile.lastModified());
            String src = MessageFormat.format(descTemplete, courseNo);
            CourseFile descCourseFile = new CourseFile("html", src, time);
            fileList.add(descCourseFile);
        }

        if (Constants.page_type_package.equals(type)) {
            for (CourseFile f : fileList) {
                f.setSetid(courseNo);
            }
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
        } else {
            return null;
        }
    }

    /**
     * 获取临时图片流
     * 
     * @param id
     * @return
     * @throws IOException
     */
    public byte[] getTmpPhoto(String id) throws IOException {
        File f = new File(tmpPhotoFloderPath + id + ".jpg");
        if (f.exists()) {
            return FileUtils.readFileToByteArray(f);
        } else {
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

    public String savaTmpPhoto(byte[] content) throws IOException {
        String photoName = String.valueOf(System.nanoTime());
        if (content != null && content.length > 0) {
            File f = new File(tmpPhotoFloderPath + photoName + ".jpg");
            FileUtils.writeByteArrayToFile(f, content);
        }
        return photoName;
    }

    /**
     * @param fileToUpload
     * @param id 对应宿主id
     * @param index 图片序列号
     * @param type 图片类型 1-普通菜品 2-套餐
     * @throws IOException
     */
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

    /**
     * 依据临时图片id 保存图片
     * 
     * @param tmpid 临时图片id
     * @param id 宿主id
     * @param index 序列号
     * @param type 图片类型 1-普通菜品 2-套餐
     * @throws IOException
     */
    public void savePhotoByTmpPhoto(String tmpid, String id, String index, String type) throws IOException {
        String path = getPath(type, false);

        File floder = new File(path + id);
        if (!floder.exists()) {
            FileUtils.forceMkdir(floder);
        }
        byte[] content = getTmpPhoto(tmpid);
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
