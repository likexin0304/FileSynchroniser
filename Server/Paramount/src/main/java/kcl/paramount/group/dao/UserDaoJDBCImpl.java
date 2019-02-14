package kcl.paramount.group.dao;



import kcl.paramount.group.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String LOGIN = "select * from user where username=? and password=?";
    private static final String CHECK_USER = "select * from user where username=?";
    private static final String ADD_USER = "insert into user(username,password,answer1,answer2) values (?,?,?,?)";
    private static final String CHANGE_PASSWORD = "update user set password=? where username=?";
    private static final String CHECK_ANSWER = "select * from user where username=? and answer1=? and answer2=?";

    private DBUtils utils = null;
    private  Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public UserDaoJDBCImpl() {
        utils = DBUtils.getInstance();
    }

    @Override
    public Boolean login(String username, String password) {
        Boolean flag = false;

        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(LOGIN);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rset = pstmt.executeQuery();

            if(rset.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return flag;
    }

    /*
    true : user exist
    false : user do not exist
     */
    @Override
    public Boolean chechUser(String username) {
        Boolean flag = false;

        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(CHECK_USER);
            pstmt.setString(1, username);
            rset = pstmt.executeQuery();
            if(rset.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return flag;
    }

    @Override
    public Boolean addUser(String username, String password, String answer1, String answer2) {
        Boolean flag = true;

        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(ADD_USER);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, answer1);
            pstmt.setString(4, answer2);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return flag;
    }

    @Override
    public Boolean changePassword(String username, String newPassword) {
        Boolean flag = true;

        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(CHANGE_PASSWORD);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            flag = false;
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return flag;
    }

    @Override
    public Boolean checkAnswer(String username, String answer1, String answer2) {
        Boolean flag = false;

        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(CHECK_ANSWER);
            pstmt.setString(1, username);
            pstmt.setString(2, answer1);
            pstmt.setString(3, answer2);
            rset = pstmt.executeQuery();
            if(rset.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return flag;
    }
}
