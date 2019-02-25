package kcl.paramount.group.controller;

import kcl.paramount.group.business.FileBusiness;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@ResponseBody
public class UploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("username") String username,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("url") String url) {
        String result = null;
        FileBusiness fb = new FileBusiness();
        result = fb.upload(username, file, url);
        return result;
    }

}
