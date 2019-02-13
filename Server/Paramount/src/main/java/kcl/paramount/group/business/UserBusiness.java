package kcl.paramount.group.business;

import com.alibaba.fastjson.JSONObject;
import kcl.paramount.group.dao.UserDao;
import kcl.paramount.group.dao.UserDaoJDBCImpl;
import kcl.paramount.group.util.JSONUtils;
import kcl.paramount.group.util.MD5Utils;
import sun.security.provider.MD5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserBusiness {

    public String userLogin(String username, String password) {
        Boolean flag = false;
        String result = null;
        String hashed = MD5Utils.getMD5(password);
        UserDao userDao = new UserDaoJDBCImpl();
        flag = userDao.login(username, hashed);
        if (flag) {
            result = JSONUtils.getJSONString("success", "");
        }
        else {
            if (userDao.chechUser(username) == false) {
                result = JSONUtils.getJSONString("fail", "user do not exist");
            }
            else {
                result = JSONUtils.getJSONString("fail", "username or password incorrect");
            }
        }
        return result;
    }

    public String addUser(String username, String password, String answer1, String answer2) {
        String result = null;
        UserDao userDao = new UserDaoJDBCImpl();
        password = MD5Utils.getMD5(password);
        answer1 = MD5Utils.getMD5(answer1);
        answer2 = MD5Utils.getMD5(answer2);
        // false means user do not exist
        if (userDao.chechUser(username) == false) {
            boolean flag = userDao.addUser(username, password, answer1, answer2);
            if (flag) {
                result = JSONUtils.getJSONString("success", "");
            }
            else {
                result = JSONUtils.getJSONString("fail", "unknown reasons");
            }
        }
        else {
            result = JSONUtils.getJSONString("fail", "user already existed");
        }
        return result;
    }

    public String changePassword(String username, String password, String newPassword) {
        String result = null;
        String hashed = MD5Utils.getMD5(password);
        UserDao userDao = new UserDaoJDBCImpl();
        Boolean flag = userDao.login(username, hashed);
        if(flag) {
            hashed = MD5Utils.getMD5(newPassword);
            flag = userDao.changePassword(username, hashed);
            if(flag) {
                result = JSONUtils.getJSONString("success", "");
            }
            else {
                result = JSONUtils.getJSONString("fail", "unknown reasons");
            }
        }
        else {
            result = JSONUtils.getJSONString("fail", "the password unmatched the original password");
        }

        return result;
    }


}
