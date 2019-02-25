package kcl.paramount.group.dao;

import kcl.paramount.group.entity.Dirs;
import kcl.paramount.group.entity.Files;
import kcl.paramount.group.util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CatalogDetailDaoJDBCImpl implements CatalogDetailDao {

    private static final String GET_DIR_DETAIL = "select url from dirs where username=? and pre=?";
    private static final String GET_FILE_DETAIL = "select url,timef,size from files where username=? and pre=?";
    private static final String GET_DIR_DETAIL_N = "select url from dirs where username=? and pre is null";
    private static final String GET_FILE_DETAIL_N = "select url,timef,size from files where username=? and pre is null";

    private DBUtils utils = null;
    private Connection conn = null;
    private PreparedStatement pstmt = null;
    private ResultSet rset = null;

    public CatalogDetailDaoJDBCImpl() {
        utils = DBUtils.getInstance();
    }

    @Override
    public List<String> getDirs(String username, String url) {
        List<String> result = new ArrayList<>();
        try {
            conn = utils.getConn();
            if (url != null) {
                pstmt = conn.prepareStatement(GET_DIR_DETAIL);
                pstmt.setString(1, username);
                pstmt.setString(2, url);
            }
            else {
                pstmt = conn.prepareStatement(GET_DIR_DETAIL_N);
                pstmt.setString(1, username);
            }
            rset = pstmt.executeQuery();
            while (rset.next()) {
                Dirs tmp = new Dirs();
                tmp.setUrl(rset.getString(1));
                result.add(tmp.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }


    @Override
    public List<String> getFiles(String username, String url) {
        List<String> result = new ArrayList<>();
        try {
            conn = utils.getConn();
            if (url != null) {
                pstmt = conn.prepareStatement(GET_FILE_DETAIL);
                pstmt.setString(1, username);
                pstmt.setString(2, url);
            }
            else {
                pstmt = conn.prepareStatement(GET_FILE_DETAIL_N);
                pstmt.setString(1, username);
            }
            rset = pstmt.executeQuery();
            while (rset.next()) {
                Files tmp = new Files();
                tmp.setUrl(rset.getString(1));
                tmp.setTime(rset.getString(2));
                tmp.setSize(rset.getString(3));
                result.add(tmp.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }
}
