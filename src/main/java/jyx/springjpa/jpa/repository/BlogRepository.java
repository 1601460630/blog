package jyx.springjpa.jpa.repository;

import jyx.springjpa.jpa.domain.Blog;
import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author jyxlalala
 * @Date 2019/3/26 22:02
 * @Version 1.0
 */
public interface BlogRepository extends JpaRepository<Blog, Long> {
    /**
     * 根据用户名查、博客标题查询博客列表
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> findByUserAndTitleLike(User user, String title, Pageable pageable);

    /**
     * 根据用户名、博客查询博客列表
     * @param title
     * @param user
     * @param tags
     * @param user2
     * @param pageable
     * @return
     */
    Page<Blog> findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(String title, User user, String tags, User user2, Pageable pageable);

    /**
     * 根据分类查询博客列表
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> findByCatalog(Catalog catalog,Pageable pageable);
}
