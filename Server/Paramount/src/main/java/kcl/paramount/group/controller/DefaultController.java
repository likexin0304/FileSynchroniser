package kcl.paramount.group.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*
    the main path of the application, most use for test
 */
@RestController
public class DefaultController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "We are group Paramount!";
    }
}
