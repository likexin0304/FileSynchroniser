package kcl.paramount.group.controller;

import kcl.paramount.group.business.UserBusiness;
import kcl.paramount.group.util.JSONUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/*
    basic handle login request
    need: username, password
 */
@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String result = null;
        UserBusiness ub = new UserBusiness();
        result = ub.userLogin(username, password);
        return result;
    }

    //invoke get request method
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginP(@RequestParam("username") String username, @RequestParam("password") String password) {
        return login(username, password);
    }

}
