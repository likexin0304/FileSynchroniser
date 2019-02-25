package kcl.paramount.group.dao;

import java.util.List;

public interface CatalogDao {
    List<String> getCatalog(String username, String url);
    List<String> getFile(String username, String url);
}
