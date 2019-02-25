package kcl.paramount.group.dao;

import kcl.paramount.group.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FileDaoJDBCImpl implements FileDao {

    private static final String C_DIR = "select * from dirs where username=? and pre=?";
    private static final String C_FILE = "select * from files where username=? and pre=?";
    private static final String D_DIR = "delete from dirs where username=? and url=?";
    private static final String D_FILE = "delete from files where username=? and url=?";

    private DBUtils utils = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public FileDaoJDBCImpl() {
        utils = DBUtils.getInstance();
    }

    @Override
    public Boolean checkPreDic(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(C_DIR);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean checkPreFile(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(C_FILE);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                result = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean delectInDir(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(D_DIR);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            result = false;
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean delectInFile(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(D_FILE);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            pstmt.executeUpdate();
            System.out.println(pstmt.toString());
        } catch (SQLException e) {
            result = false;
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }
}
