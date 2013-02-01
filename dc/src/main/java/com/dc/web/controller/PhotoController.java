package com.dc.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dc.constants.Constants;
import com.dc.service.PageService;

@Controller
public class PhotoController extends AbstractController {

    @Autowired
    private PageService pageService;

    /**
     * 菜品图片
     * 
     * @param courseNo
     * @param index
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/photo/{courseNo}/{index}.jpg", method = RequestMethod.GET)
    public void requestPhoto(@PathVariable String courseNo, @PathVariable String index, HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String imagePath = request.getSession().getServletContext().getRealPath("/image/no_image.gif");
        byte[] photo = pageService.getPhoto(courseNo, index, Constants.page_type_course);
        if (photo == null) {
            photo = FileUtils.readFileToByteArray(new File(imagePath));
        }
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=" + courseNo + ".jpg");
        response.setHeader("Content-Length", String.valueOf(photo.length));
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(photo);
        sout.flush();
    }

    /**
     * 套餐图片
     * 
     * @param id
     * @param index
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/packagephoto/{id}/{index}.jpg", method = RequestMethod.GET)
    public void requestPackagePhoto(@PathVariable String id, @PathVariable String index, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String imagePath = request.getSession().getServletContext().getRealPath("/image/no_image.gif");
        byte[] photo = pageService.getPhoto(id, index, Constants.page_type_package);
        if (photo == null) {
            photo = FileUtils.readFileToByteArray(new File(imagePath));
        }
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=" + id + ".jpg");
        response.setHeader("Content-Length", String.valueOf(photo.length));
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(photo);
        sout.flush();
    }

    /**
     * 临时图片
     * 
     * @param id
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/tmpphoto/{id}.jpg", method = RequestMethod.GET)
    public void requestTmpPhoto(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imagePath = request.getSession().getServletContext().getRealPath("/image/no_image.gif");
        byte[] photo = pageService.getTmpPhoto(id);
        if (photo == null) {
            photo = FileUtils.readFileToByteArray(new File(imagePath));
        }
        response.setContentType("image/jpeg");
        response.setHeader("Content-Disposition", "inline; filename=" + id + ".jpg");
        response.setHeader("Content-Length", String.valueOf(photo.length));
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(photo);
        sout.flush();
    }

    @RequestMapping(value = "/uploadphoto", method = RequestMethod.POST)
    public void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {
        byte[] photoByte = IOUtils.toByteArray(request.getInputStream());
        String name = pageService.savaTmpPhoto(photoByte);
        response.getWriter().print(name);
    }

    @RequestMapping(value = "/packagedesc/{id}.htm", method = RequestMethod.GET)
    public void requestPackageDesc(@PathVariable String courseNo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String desc = pageService.getDesc(courseNo, Constants.page_type_package);
        response.setContentType("text/html");
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(desc.getBytes());
        sout.flush();
    }

    @RequestMapping(value = "/desc/{courseNo}.htm", method = RequestMethod.GET)
    public void requestPhoto(@PathVariable String courseNo, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String desc = pageService.getDesc(courseNo, Constants.page_type_course);
        response.setContentType("text/html");
        javax.servlet.ServletOutputStream sout = response.getOutputStream();
        sout.write(desc.getBytes());
        sout.flush();
    }
}
