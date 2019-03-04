package kcl.paramount.group.dao;

import kcl.paramount.group.entity.Files;

public interface FileDao {
    Boolean checkPreDic(String username, String url);
    Boolean checkPreFile(String username, String url);
    Boolean delectInDir(String username, String url);
    Boolean delectInFile(String username, String url);
    Boolean checkFile(String username, String url);
    Boolean addFile(String username, String url, String time, String pre, Long size);
    Boolean addDir(String username, String url, String prel);
    Boolean lock(String username, String url);
    Boolean unlock(String username, String url);
    Boolean isLock(String username, String url);
    Boolean updateFile(String username, String url);
    Boolean updateSize(String username, String url, long size);
    Boolean rename(String username, String url, String newUrl);
    Files detail(String username, String url);
}
