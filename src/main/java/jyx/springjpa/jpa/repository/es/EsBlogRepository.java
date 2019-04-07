package jyx.springjpa.jpa.repository.es;

import jyx.springjpa.jpa.domain.es.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author jyxlalala
 * @Date 2019/3/31 14:32
 * @Version 1.0
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {

    /**
     * ģ����ѯ
     *
     * @param title
     * @param summary
     * @param content
     * @param tags
     * @param pageable
     * @return
     */
    Page<EsBlog> findByTitleContainingOrSummaryContainingOrContentContainingOrTagsContaining(String title, String summary, String content, String tags, Pageable pageable);

    /**
     * ����Blog��id��ѯEsBlog
     * @param blogId
     * @return
     */
    EsBlog findByBlogId(Long blogId);
}
