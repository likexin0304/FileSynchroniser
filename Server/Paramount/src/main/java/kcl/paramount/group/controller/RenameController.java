package kcl.paramount.group.controller;

import kcl.paramount.group.business.FileBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RenameController {

    @RequestMapping(value = "/rename", method = RequestMethod.GET)
    public String rename(@RequestParam("username") String username,
                         @RequestParam("url") String url,
                         @RequestParam("newUrl") String newUrl) {
        String result = null;
        FileBusiness fb = new FileBusiness();
        result = fb.rename(username, url, newUrl);
        return result;
    }

    //invoke get request method
    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    public String renameP(@RequestParam("username") String username,
                          @RequestParam("url") String url,
                          @RequestParam("newUrl") String newUrl) {
        return rename(username, url, newUrl);
    }

}