package kcl.paramount.group.util;

import java.sql.*;

public class DBUtils {

    private static final DBUtils UTILS = new DBUtils();
    private String url = null;
    private String user = null;
    private String password = null;

    /*
    Singleton Pattern
     */
//    private DBUtils() {
//        XMLUtils xmlUtils = new XMLUtils();
//        url = xmlUtils.getAttribute("url");
//        user = xmlUtils.getAttribute("user");
//        password = xmlUtils.getAttribute("password");
//    }

    private DBUtils() {
        url = "jdbc:mysql://35.178.35.227:3306/Paramount";
        user = "root";
        password = "";
    }

    public static DBUtils getInstance() {
        return UTILS;
    }

    /*
    Get the database connection
     */
    public Connection getConn() {
        Connection conn = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            //auto generated catch block
            e.printStackTrace();
            System.out.println("Mysql class not found !");
        } catch (SQLException e) {
            //auto generated catch block
            e.printStackTrace();
        }

        return conn;
    }

    /*
    Release the resources
     */
    public void releaseRes(Connection conn, PreparedStatement pstmt, ResultSet rset) {
        try {
            if (rset != null) {
                rset.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            //auto generated catch block
            e.printStackTrace();
        }
    }

}
