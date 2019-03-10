package kcl.paramount.group.business;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonAlias;
import kcl.paramount.group.dao.FileDao;
import kcl.paramount.group.dao.FileDaoJDBCImpl;
import kcl.paramount.group.entity.Files;
import kcl.paramount.group.util.JSONUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileBusiness {

    private static final String ROOT_PATH = "/home/upload/";

    /*
        check target directory existed or not
        if not => create it
        upload file
     */
    public String upload(String username, MultipartFile file, String url) {
        FileDao fd = new FileDaoJDBCImpl();
        String result = null;
        String checkurl;
        if (url.equals("/") == false) {
            if (url.endsWith("/")) {
                checkurl = url + file.getOriginalFilename();
            }
            else {
                checkurl = url + "/" + file.getOriginalFilename();
            }
        }
        else {
            checkurl = file.getOriginalFilename();
        }
        Boolean flag = fd.checkFile(username, checkurl);
        if (flag == true) {
            if (fd.isLock(username, checkurl) == true) {
                result = JSONUtils.getJSONString("fail", "uploading conflict");
            }
            else {
                fd.lock(username, checkurl);
                result = addUpdatedFile(username, file, url);
                fd.updateFile(username, checkurl);
                fd.unlock(username, checkurl);
            }
        }
        else{
            result = addNewFile(username, file, url);
        }
        return result;
    }

    /*
        delete the file
        check the version of file
        if it possible, delete both two versions of the file
     */
    public String delete(String username, String url) {
        String result = null;
        FileDao fd = new FileDaoJDBCImpl();
        Integer vi = fd.getFileVersion(username, url);
        Boolean flag1 = fd.checkPreDic(username, url);
        Boolean flag2 = fd.checkPreFile(username, url);
        if (flag1 && flag2) {
            flag1 = fd.delectInFile(username, url);
            flag2 = fd.delectInDir(username, url);
            if (flag1 && flag2) {
                if (url.startsWith("/") == false) {
                    url = "/" + url;
                }
                Integer p = url.lastIndexOf("/");
                String pre = url.substring(0, p + 1);
                String post = url.substring(p + 1, url.length());
                String nowVersion = String.valueOf(vi % 2) + "-";
                String otherVersion = String.valueOf((vi + 1) % 2) + "-";
                String delteFile;
                delteFile = ROOT_PATH + username + pre + nowVersion + post;
                File file = new File(delteFile);
                file.delete();
                delteFile = ROOT_PATH + username + pre + otherVersion + post;
                file = new File(delteFile);
                if (file.exists()) {
                    file.delete();
                }
                result = JSONUtils.getJSONString("success", "");
            }
            else {
                result = JSONUtils.getJSONString("fail", "unknown reasons");
            }
        }
        else {
            result = JSONUtils.getJSONString("fail", "the directory may not empty");
        }
        return result;
    }

    public String detail(String username, String url) {
        String result = JSONUtils.getJSONString("fail", "unknown reasons");
        FileDao fd = new FileDaoJDBCImpl();
        Files fileDetail = fd.detail(username, url);
        if (fileDetail != null) {
            result = JSONUtils.getJSONString("success", fileDetail.toString());
        }
        return result;
    }

    /*
        rename both 0- 1-file
        little bugs:
            will change the modified time of the file
     */
    public String rename(String username, String url, String newUrl) {
        String result = null;
        File file = new File(newUrl);
        String name = file.getName();
        if (name.contains(".") == false) {
            result = JSONUtils.getJSONString("fail", "the type of file if missed");
        }
        else {
            FileDao fd = new FileDaoJDBCImpl();
            Integer vi = fd.getFileVersion(username, url);
            String nowVersion = String.valueOf(vi % 2) + "-";
            String otherVersion = String.valueOf((vi + 1) % 2) + "-";
            if (fd.checkFile(username, newUrl) == true) {
                result = JSONUtils.getJSONString("fail", "the new file name is conflicted");
            } else {
                if (fd.rename(username, url, newUrl)) {
                    Integer p = url.lastIndexOf("/");
                    String pre = url.substring(0, p + 1);
                    String post = url.substring(p + 1, url.length());
                    p = newUrl.lastIndexOf("/");
                    String newPre = newUrl.substring(0, p + 1);
                    String newPost = newUrl.substring(p + 1, url.length());
                    String oldPath = ROOT_PATH + username;
                    String oldPath1 = ROOT_PATH + username;
                    if (url.startsWith("/")) {
                        oldPath = oldPath + pre + nowVersion + post;
                        oldPath1 = oldPath1 + pre + otherVersion + post;
                    }
                    else {
                        oldPath = oldPath + "/" + pre + nowVersion + post;
                        oldPath1 = oldPath1 + "/" + pre + otherVersion + post;
                    }
                    String newPath = ROOT_PATH + username;
                    String newPath1 = ROOT_PATH + username;
                    if (newUrl.startsWith("/")) {
                        newPath = newPath + newPre + nowVersion + newPost;
                        newPath1 = newPath1 + newPre + otherVersion + newPost;
                    }
                    else {
                        newPath = newPath + "/" + newPre + nowVersion + newPost;
                        newPath1 = newPath1 + "/" + newPre + otherVersion + newPost;
                    }
                    File oldFile = new File(oldPath);
                    if (oldFile.renameTo(new File(newPath))){
                        result = JSONUtils.getJSONString("success", "");
                    }
                    else {
                        result = JSONUtils.getJSONString("fail", "rename error");
                    }
                    oldFile = new File(oldPath1);
                    if (oldFile.exists()) {
                        if (oldFile.renameTo(new File(newPath1)) == false) {
                            result = JSONUtils.getJSONString("fail", "rename error");
                        }
                    }
                } else {
                    result = JSONUtils.getJSONString("fail", "unknown reaseons");
                }
            }
        }

        return result;
    }

    /*
        revert the file to the previous version
     */
    public String revert(String username, String url) {
        String result = null;
        String filePath;
        FileDao fd = new FileDaoJDBCImpl();
        Integer vi = fd.getFileVersion(username, url);
        if (vi == 0) {
            result = JSONUtils.getJSONString("fail", "can not revert first version");
        }
        else {
            String storedName = url;
            String lastVersion = String.valueOf((vi - 1) % 2) + "-";
            String nowVersion = String.valueOf(vi % 2) + "-";
            if (url.startsWith("/") == false) {
                url = "/" + url;
            }
            Integer p = url.lastIndexOf("/");
            String pre = url.substring(0, p + 1);
            String post = url.substring(p + 1, url.length());
            filePath = ROOT_PATH + username + pre + lastVersion + post;
            File file = new File(filePath);
            if (file.exists() == false) {
                result = JSONUtils.getJSONString("fail", "can not revert twice");
            }
            else {
                try {
                    FileInputStream fis = null;
                    fis = new FileInputStream(file);
                    FileChannel fc = fis.getChannel();
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String time = ft.format(file.lastModified());
                    File deleteFile = new File(ROOT_PATH + username + pre + nowVersion + post);
                    if (fd.revert(username, storedName, fc.size(), time) && deleteFile.delete()) {
                        result = JSONUtils.getJSONString("success", "");
                    } else {
                        result = JSONUtils.getJSONString("fail", "unknown reasons");
                    }
                } catch (IOException e) {
                    result = JSONUtils.getJSONString("fail", e.getMessage());
                }
            }
        }
        return result;
    }

    //judge the target directory existed or not
    private Boolean judegeDirExisted(String url) {
        Boolean result = false;
        File file = new File(url);
        if (file.exists()) {
            if (file.isDirectory()) {
                result = true;
            }
        }
        return result;
    }

    //create target directories
    private void createDirectory(String url) {
        File file = new File(url);
        file.mkdirs();
    }

    private String getTime() {
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return ft.format(dNow);
    }

    private String addNewFile(String username, MultipartFile file, String url) {
        String result = null;
        if(file.isEmpty()) {
            result = JSONUtils.getJSONString("fail", "file is empty");
        }
        else {
            String filePath;
            if (url.startsWith("/")) {
                filePath = ROOT_PATH + username + url;
            }
            else {
                filePath = ROOT_PATH + username + "/" + url;
            }
            if (filePath.endsWith("/") == false) {
                filePath = filePath + "/";
            }
            if (judegeDirExisted(filePath) == false) {
                createDirectory(filePath);
            }

            String fileName = file.getOriginalFilename();
            File dest = new File(filePath + "0-" +fileName);
            try {
                String pre;
                if (url.endsWith("/") == false) {
                    url = url + "/";
                }
                String storedName = null;
                if (url.equals("/")) {
                    storedName = fileName;
                }
                else {
                    storedName = url + fileName;
                }
                file.transferTo(dest);
                FileDao fd = new FileDaoJDBCImpl();
                pre = url.substring(0, url.length() - 1);
                FileInputStream fis = new FileInputStream(dest);
                FileChannel fc = fis.getChannel();
                fd.addFile(username, storedName, getTime(), pre, fc.size());
                result = JSONUtils.getJSONString("success", "");
            } catch (IOException e) {
                result = JSONUtils.getJSONString("fail", e.getMessage());
            }
        }
        return result;
    }

    private String addUpdatedFile(String username, MultipartFile file, String url) {
        String result = null;
        if(file.isEmpty()) {
            result = JSONUtils.getJSONString("fail", "file is empty");
        }
        else {
            String filePath;
            if (url.startsWith("/")) {
                filePath = ROOT_PATH + username + url;
            }
            else {
                filePath = ROOT_PATH + username + "/" + url;
            }
            if (filePath.endsWith("/") == false) {
                filePath = filePath + "/";
            }
            if (judegeDirExisted(filePath) == false) {
                createDirectory(filePath);
            }

            String fileName = file.getOriginalFilename();

            try {
                if (url.endsWith("/") == false) {
                    url = url + "/";
                }
                String storedName = null;
                if (url.equals("/")) {
                    storedName = fileName;
                }
                else {
                    storedName = url + fileName;
                }
                FileDao fd = new FileDaoJDBCImpl();
                Integer vi = fd.getFileVersion(username, storedName);
                String version = String.valueOf((vi + 1) % 2) + "-";
                File dest = new File(filePath + version + fileName);
                file.transferTo(dest);
                result = JSONUtils.getJSONString("success", "");
                FileInputStream fis = new FileInputStream(dest);
                FileChannel fc = fis.getChannel();
                fd.updateSize(username, storedName, fc.size(), getTime());
            } catch (IOException e) {
                result = JSONUtils.getJSONString("fail", e.toString());
            }
        }
        return result;
    }

}
