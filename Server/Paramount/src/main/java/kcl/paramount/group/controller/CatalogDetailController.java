package kcl.paramount.group.controller;

import kcl.paramount.group.business.CatalogDetailBusiness;
import kcl.paramount.group.dao.CatalogDetailDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogDetailController {
    @RequestMapping(value = "/dirsroot", method = RequestMethod.GET)
    public String getDirs(@RequestParam("username") String username) {
        String result = null;
        CatalogDetailBusiness cb = new CatalogDetailBusiness();
        result = cb.getDetailDirs(username, null);
        return result;
    }

    @RequestMapping(value = "/dirs", method = RequestMethod.GET)
    public String getDirs(@RequestParam("username") String username,
                             @RequestParam("url") String url) {
        String result = null;
        CatalogDetailBusiness cb = new CatalogDetailBusiness();
        result = cb.getDetailDirs(username, url);
        return result;
    }

    @RequestMapping(value = "/filesroot", method = RequestMethod.GET)
    public String getFiles(@RequestParam("username") String username) {
        String result = null;
        CatalogDetailBusiness cb = new CatalogDetailBusiness();
        result = cb.getDetailFiles(username, null);
        return result;
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public String getFiles(@RequestParam("username") String username,
                             @RequestParam("url") String url) {
        String result = null;
        CatalogDetailBusiness cb = new CatalogDetailBusiness();
        result = cb.getDetailFiles(username, url);
        return result;
    }
}
