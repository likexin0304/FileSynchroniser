package kcl.paramount.group.dao;

import kcl.paramount.group.entity.Dirs;
import kcl.paramount.group.entity.Files;

import java.util.List;

public interface CatalogDetailDao {
    List<String> getDirs(String username, String url);
    List<String> getFiles(String username, String url);
}
