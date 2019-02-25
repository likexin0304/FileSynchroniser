package kcl.paramount.group.controller;

import kcl.paramount.group.business.FileBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class DeleteController {
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(@RequestParam("username") String username,
                             @RequestParam("url") String url) {
        String result = null;
        FileBusiness cb = new FileBusiness();
        result = cb.delete(username, url);
        return result;
    }
}
