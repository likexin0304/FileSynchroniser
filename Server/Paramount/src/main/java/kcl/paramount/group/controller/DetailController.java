package kcl.paramount.group.controller;

import kcl.paramount.group.business.FileBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DetailController {
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(@RequestParam("username") String username,
                         @RequestParam("url") String url) {
        String result = null;
        FileBusiness fb = new FileBusiness();
        result = fb.detail(username, url);
        return result;
    }
}
