package kcl.paramount.group.controller;

import kcl.paramount.group.business.CatalogBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatalogController {
    @RequestMapping(value = "/catalogroot", method = RequestMethod.GET)
    public String getCatalog(@RequestParam("username") String username) {
        String result = null;
        CatalogBusiness cb = new CatalogBusiness();
        result = cb.getCatalog(username, null);
        return result;
    }

    @RequestMapping(value = "/catalog", method = RequestMethod.GET)
    public String getCatalog(@RequestParam("username") String username,
                             @RequestParam("url") String url) {
        String result = null;
        CatalogBusiness cb = new CatalogBusiness();
        result = cb.getCatalog(username, url);
        return result;
    }
}
