package kcl.paramount.group.dao;

public interface FileDao {
    Boolean checkPreDic(String username, String url);
    Boolean checkPreFile(String username, String url);
    Boolean delectInDir(String username, String url);
    Boolean delectInFile(String username, String url);
}
