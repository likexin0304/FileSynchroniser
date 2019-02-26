package kcl.paramount.group.business;

import kcl.paramount.group.dao.FileDao;
import kcl.paramount.group.dao.FileDaoJDBCImpl;
import kcl.paramount.group.util.JSONUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
            File dest = new File(filePath + fileName);
            try {
                FileDao fd = new FileDaoJDBCImpl();
                String pre;
                if (url.endsWith("/") == false) {
                    url = url + "/";
                }
                pre = url.substring(0, url.length() - 1);
                fd.addFile(username, url + fileName, getTime(), pre, dest.length());
                file.transferTo(dest);
                result = JSONUtils.getJSONString("success", "");
            } catch (IOException e) {
                result = JSONUtils.getJSONString("fail", "unknown reasons");
            }
        }
        return result;
    }

    public String delete(String username, String url) {
        String result = null;
        FileDao fd = new FileDaoJDBCImpl();
        Boolean flag1 = fd.checkPreDic(username, url);
        Boolean flag2 = fd.checkPreFile(username, url);
        if (flag1 && flag2) {
            flag1 = fd.delectInFile(username, url);
            flag2 = fd.delectInDir(username, url);
            if (flag1 && flag2) {
                if (url.startsWith("/") == false) {
                    url = "/" + url;
                }
                url = "/home/upload/" + username + url;
                File file = new File(url);
                file.delete();
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
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        return ft.format(dNow);
    }

}
