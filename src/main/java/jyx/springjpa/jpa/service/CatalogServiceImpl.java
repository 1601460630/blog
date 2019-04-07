package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 23:23
 * @Version 1.0
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Override
    public Catalog saveCatalog(Catalog catalog) {

        //判断重复
        List<Catalog> list = catalogRepository.findByUserAndName(catalog.getUser(), catalog.getName());

        if (list != null && list.size() > 0) {
            throw new IllegalArgumentException("该分类已存在");
        }
        return catalogRepository.save(catalog);
    }

    @Override
    public void removeCatalog(Long id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public Optional<Catalog> getCatalogById(Long id) {
        return catalogRepository.findById(id);
    }

    @Override
    public List<Catalog> listCatalogs(User user) {
        return catalogRepository.findByUser(user);
    }
}
