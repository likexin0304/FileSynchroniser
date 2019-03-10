package kcl.paramount.group.controller;

import kcl.paramount.group.business.FileBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RevertController {

    @RequestMapping(value = "/revert", method = RequestMethod.GET)
    public String upload(@RequestParam("username") String username,
                         @RequestParam("url") String url) {
        String result = null;
        FileBusiness fb = new FileBusiness();
        result = fb.revert(username, url);
        return result;
    }

}