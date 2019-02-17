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

/*
    the business layer to manage request
 */
public class UserBusiness {

    /*
        use hashed password and username to login
        if fail => check user whether exist or not
     */
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

    /*
        check user exist or not
        hash the password and security answers
        then add the user
     */
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

    /*
        change password only can used after login, so use must be existed
        check the old password whether match
        if match => change to the new password
     */
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

    /*
      1. check user exist or not
      2. check the security answers match or not
      3. change the password
     */
    public String forgetPassword(String username,
                                 String answer1,
                                 String answer2,
                                 String password) {
        String result = null;

        UserDao userDao = new UserDaoJDBCImpl();
        Boolean flag = userDao.chechUser(username);

        if (flag) {
            String hashed1 = MD5Utils.getMD5(answer1);
            String hashed2 = MD5Utils.getMD5(answer2);
            flag = userDao.checkAnswer(username, hashed1, hashed2);
            if (flag) {
                String hashed = MD5Utils.getMD5(password);
                flag = userDao.changePassword(username, hashed);
                if (flag) {
                    result = JSONUtils.getJSONString("success", "");
                }
                else {
                    result = JSONUtils.getJSONString("fail", "unknown reasons");
                }
            }
            else {
                result = JSONUtils.getJSONString("fail", "the answer(s) unmatch");
            }
        }
        else {
            result = JSONUtils.getJSONString("fail", "user do not exist");
        }

        return result;
    }


}
