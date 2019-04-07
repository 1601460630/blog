package jyx.springjpa.jpa.vo;

import jyx.springjpa.jpa.domain.Catalog;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 23:14
 * @Version 1.0
 */
public class CatalogVO {

    private String username;
    private Catalog catalog;

    public CatalogVO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
}
