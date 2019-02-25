package kcl.paramount.group.dao;

import kcl.paramount.group.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDaoJDBCImpl implements CatalogDao {
    private static final String GET_CATALOG = "select url from dirs where username=? and pre=?";
    private static final String GET_FILE = "select url from files where username=? and pre=?";
    private static final String GET_CATALOG_N = "select url from dirs where username=? and pre is null";
    private static final String GET_FILE_N = "select url from files where username=? and pre is null";

    private DBUtils utils = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public CatalogDaoJDBCImpl() {
        utils = DBUtils.getInstance();
    }

    @Override
    public List<String> getCatalog(String username, String url) {
        List<String> result = new ArrayList<>();
        try {
            conn = utils.getConn();
            if (url != null) {
                pstmt = conn.prepareStatement(GET_CATALOG);
                pstmt.setString(1, username);
                pstmt.setString(2, url);
            }
            else {
                pstmt = conn.prepareStatement(GET_CATALOG_N);
                pstmt.setString(1, username);
            }
            rset = pstmt.executeQuery();
            while (rset.next()) {
                result.add(rset.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public List<String> getFile(String username, String url) {
        List<String> result = new ArrayList<>();
        try {
            conn = utils.getConn();
            if (url != null) {
                pstmt = conn.prepareStatement(GET_FILE);
                pstmt.setString(1, username);
                pstmt.setString(2, url);
            }
            else {
                pstmt = conn.prepareStatement(GET_FILE_N);
                pstmt.setString(1, username);
            }
            rset = pstmt.executeQuery();
            while (rset.next()) {
                result.add(rset.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }
}
