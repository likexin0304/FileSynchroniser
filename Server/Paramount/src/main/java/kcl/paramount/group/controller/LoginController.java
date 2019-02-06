package kcl.paramount.group.controller;

import kcl.paramount.group.business.UserBusiness;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        Boolean flag = false;

        UserBusiness ub = new UserBusiness();
        flag = ub.userLogin(username, password);

        String result = flag? "Success" : "Fail" ;
        return result;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hi";

    }

}
