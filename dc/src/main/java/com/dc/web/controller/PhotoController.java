package com.dc.web.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dc.service.PageService;

@Controller
public class PhotoController extends AbstractController {

    @Autowired
    private PageService pageService;

    @RequestMapping(value = "/photo/{courseNo}/{index}.jpg", method = RequestMethod.GET)
    public void requestPhoto(@PathVariable String courseNo, @PathVariable String index, HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        String imagePath = request.getSession().getServletContext().getRealPath("/image/no_image.gif");
        byte[] photo = pageService.getPhoto(courseNo, index);
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
}
