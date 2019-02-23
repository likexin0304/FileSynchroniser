package kcl.paramount.group.controller;

import kcl.paramount.group.business.UserBusiness;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.ResponseWrapper;

/*
    basic handle change password request
    need: username, old password and new password
 */
@RestController
public class ChangePasswordController {
    @RequestMapping(value = "/changepassword", method = RequestMethod.GET)
    public String changePassword(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("newpassword") String newpassword) {
        String result = null;
        UserBusiness ub = new UserBusiness();
        result = ub.changePassword(username, password, newpassword);
        return result;
    }

    //invoke get request method
    @RequestMapping(value = "/changepassword", method = RequestMethod.POST)
    public String changePasswordP(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("newpassword") String newpassword) {
        return changePassword(username, password, newpassword);
    }
}
