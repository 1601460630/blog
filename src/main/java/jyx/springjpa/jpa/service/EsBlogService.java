package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.User;
import jyx.springjpa.jpa.domain.es.EsBlog;
import jyx.springjpa.jpa.vo.TagVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author jyxlalala
 * @Date 2019/3/31 15:08
 * @Version 1.0
 */
public interface EsBlogService {

    /**
     * 删除EsBlog
     *
     * @param id
     */
    void removeEsBlog(String id);

    /**
     * 更新EsBlog
     *
     * @param esBlog
     * @return
     */
    EsBlog updateEsBlog(EsBlog esBlog);

    /**
     * 根据Blog的id获取EsBlog
     *
     * @param blogId
     * @return
     */
    EsBlog getEsBlogByBlogId(Long blogId);

    /**
     * 最新博客列表，分页
     *
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listNewestEsBlogs(String keyword, Pageable pageable);

    /**
     * 最新博客列表，分页
     *
     * @param keyword
     * @param pageable
     * @return
     */
    Page<EsBlog> listHotestEsBlogs(String keyword, Pageable pageable);

    /**
     * 博客列表，分页
     *
     * @param pageable
     * @return
     */
    Page<EsBlog> listEsBlogs(Pageable pageable);

    /**
     * 最新前5
     *
     * @return
     */
    List<EsBlog> listTop5NewestEsBlog();

    /**
     * 最热前5
     *
     * @return
     */
    List<EsBlog> listTop5HotestEsBlog();

    /**
     * 最热前30标签
     *
     * @return
     */
    List<TagVO> listTop30Tags();

    /**
     * 最热前12用户
     *
     * @return
     */
    List<User> listTop12Users();

}
