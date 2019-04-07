package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 23:20
 * @Version 1.0
 */
public interface CatalogService {

    /**
     * 保存Catalog
     *
     * @param catalog
     * @return
     */
    Catalog saveCatalog(Catalog catalog);

    /**
     * 删除Catalog
     *
     * @param id
     */
    void removeCatalog(Long id);

    /**
     * 根据id获取Catalog
     *
     * @param id
     * @return
     */
    Optional<Catalog> getCatalogById(Long id);

    /**
     * 获取Catalog列表
     *
     * @param user
     * @return
     */
    List<Catalog> listCatalogs(User user);
}
