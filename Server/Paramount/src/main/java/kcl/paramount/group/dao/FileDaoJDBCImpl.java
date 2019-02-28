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
    private static final String CHECK_FILE = "select * from files where username=? and url=?";
    private static final String INSERT_DIRS = "insert into dirs values(?,?,?)";
    private static final String INSERT_FILE = "insert into files values(?,?,?,?,?,?,?)";
    private static final String LOCK = "update files set lockflag='1' where username=? and url =?";
    private static final String UNLOCK = "update files set lockflag='0' where username=? and url =?";
    private static final String CHECK_LOCK = "select lockflag from files where username=? and url=?";
    private static final String UPDATE = "update files set version=version+1 where username=? and url =?";
    private static final String SIZE = "update files set size=? where username=? and url =?";

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

    /*
        file existed => true
        else => false
     */
    @Override
    public Boolean checkFile(String username, String url) {
        Boolean result = false;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(CHECK_FILE);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean addFile(String username, String url, String time, String pre, Long size) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(INSERT_FILE);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            pstmt.setString(3, "0");
            pstmt.setInt(4, 0);
            pstmt.setString(5, time);
            if (pre.equals("")){
                pstmt.setString(6, null);
            }
            else {
                pstmt.setString(6, pre);
            }
            pstmt.setInt(7, size.intValue());
            pstmt.executeUpdate();
            System.out.println(pstmt.toString());
        } catch (SQLException e) {
            result = false;
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean addDir(String username, String url, String pre) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(INSERT_DIRS);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            pstmt.setString(3, pre);
            pstmt.executeUpdate();
            System.out.println(pstmt.toString());
        } catch (SQLException e) {
            result = false;
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean lock(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(LOCK);
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

    @Override
    public Boolean unlock(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(UNLOCK);
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

    /*
        lock => true
        unlocn => false
     */
    @Override
    public Boolean isLock(String username, String url) {
        Boolean result = false;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(CHECK_LOCK);
            pstmt.setString(1, username);
            pstmt.setString(2, url);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                if (rset.getString(1).equals("1")) {
                    result = true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            utils.releaseRes(conn, pstmt, rset);
        }
        return result;
    }

    @Override
    public Boolean updateFile(String username, String url) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(UPDATE);
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

    @Override
    public Boolean updateSize(String username, String url, long size) {
        Boolean result = true;
        try {
            conn = utils.getConn();
            pstmt = conn.prepareStatement(SIZE);
            pstmt.setInt(1, (int)size);
            pstmt.setString(2, username);
            pstmt.setString(3, url);
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
