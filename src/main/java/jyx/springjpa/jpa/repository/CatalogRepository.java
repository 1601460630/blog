package jyx.springjpa.jpa.repository;

import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 23:16
 * @Version 1.0
 */
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    /**
     * 根据用户查询
     *
     * @param user
     * @return
     */
    List<Catalog> findByUser(User user);

    /**
     * 根据用户和姓名查询
     *
     * @param user
     * @param name
     * @return
     */
    List<Catalog> findByUserAndName(User user, String name);
}
