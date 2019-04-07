package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Blog;
import jyx.springjpa.jpa.domain.Catalog;
import jyx.springjpa.jpa.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/26 22:08
 * @Version 1.0
 */
public interface BlogService {

    /**
     * 保存Blog
     *
     * @param blog
     * @return
     */
    Blog saveBlog(Blog blog);

    /**
     * 删除Blog
     *
     * @param id
     */
    void removeBlog(Long id);

    /**
     * 根据id获取Blog
     *
     * @param id
     * @return
     */
    Optional<Blog> getBlogById(Long id);

    /**
     * 根据用户进行博客名称模糊分页查询（最新）
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable);

    /**
     * 根据用户进行博客名称模糊分页查询（最热）
     *
     * @param user
     * @param title
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable);

    /**
     * 阅读量递增
     *
     * @param id
     */
    void readingIncrease(Long id);

    /**
     * 发表评论
     *
     * @param blogId
     * @param commentContent
     * @return
     */
    Blog createComment(Long blogId, String commentContent);

    /**
     * 删除评论
     *
     * @param blogId
     * @param commentId
     */
    void removeComment(Long blogId, Long commentId);

    /**
     * 点赞
     *
     * @param blogId
     * @return
     */
    Blog createVote(Long blogId) throws IllegalAccessException;

    /**
     * 取消点赞
     *
     * @param blogId
     * @param voteId
     */
    void removeVote(Long blogId, Long voteId);

    /**
     * 根据分类查询
     *
     * @param catalog
     * @param pageable
     * @return
     */
    Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable);
}
