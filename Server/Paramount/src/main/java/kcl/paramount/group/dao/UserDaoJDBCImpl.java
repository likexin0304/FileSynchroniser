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
}
