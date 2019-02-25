package kcl.paramount.group.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DownloadController {

    private static final String ROOT_PATH = "home/upload/";

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public String download(HttpServletResponse response,
                           @RequestParam("username") String username,
                           @RequestParam("url") String url) {
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

        File file = new File(filePath);
        // 如果文件名存在，则进行下载
        if (file.exists()) {

            // 配置文件下载
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

}


