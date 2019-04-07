package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.*;
import jyx.springjpa.jpa.domain.es.EsBlog;
import jyx.springjpa.jpa.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/26 22:16
 * @Version 1.0
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private EsBlogService esBlogService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public Blog saveBlog(Blog blog) {

        boolean isNew = (blog.getId() == null);
        EsBlog esBlog = null;

        Blog returnBlog = blogRepository.save(blog);

        if (isNew) {

            esBlog = new EsBlog(returnBlog);
        } else {

            esBlog = esBlogService.getEsBlogByBlogId(blog.getId());

            esBlog.update(returnBlog);
        }

        esBlogService.updateEsBlog(esBlog);

        return returnBlog;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void removeBlog(Long id) {

        blogRepository.deleteById(id);

        EsBlog esBlog = esBlogService.getEsBlogByBlogId(id);

        esBlogService.removeEsBlog(esBlog.getId());
    }

    @Override
    public Optional<Blog> getBlogById(Long id) {

        return blogRepository.findById(id);
    }

    @Override
    public Page<Blog> listBlogsByTitleVote(User user, String title, Pageable pageable) {

        //模糊查询
        title = "%" + title + "%";

        String tags = title;

        Page<Blog> blogs = blogRepository.findByTitleLikeAndUserOrTagsLikeAndUserOrderByCreateTimeDesc(title, user, tags, user, pageable);

        return blogs;
    }

    @Override
    public Page<Blog> listBlogsByTitleVoteAndSort(User user, String title, Pageable pageable) {

        //模糊查询
        title = "%" + title + "%";

        Page<Blog> blogs = blogRepository.findByUserAndTitleLike(user, title, pageable);

        return blogs;
    }

    @Override
    public void readingIncrease(Long id) {

        Optional<Blog> blog = blogRepository.findById(id);

        Blog blogNew = null;

        if (blog.isPresent()) {

            blogNew = blog.get();

            blogNew.setReadSize(blogNew.getReadSize() + 1);

            //在原有的阅读量基础上+1
            this.saveBlog(blogNew);
        }
    }

    @Override
    public Blog createComment(Long blogId, String commentContent) {

        Optional<Blog> optionalBlog = blogRepository.findById(blogId);

        Blog originalBlog = null;

        if (optionalBlog.isPresent()) {

            originalBlog = optionalBlog.get();

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Comment comment = new Comment(user, commentContent);

            originalBlog.addComment(comment);
        }

        return null;
    }

    @Override
    public void removeComment(Long blogId, Long commentId) {

        Optional<Blog> optionalBlog = blogRepository.findById(blogId);

        if (optionalBlog.isPresent()) {

            Blog originalBlog = optionalBlog.get();

            originalBlog.removeComment(commentId);

            this.saveBlog(originalBlog);
        }
    }

    @Override
    public Blog createVote(Long blogId) throws IllegalAccessException {

        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        Blog originalBlog = null;

        if (optionalBlog.isPresent()) {
            originalBlog = optionalBlog.get();

            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Vote vote = new Vote(user);

            boolean isExist = originalBlog.addVote(vote);

            if (isExist) {
                throw new IllegalAccessException("该用户已经点过赞了");
            }
        }

        return this.saveBlog(originalBlog);
    }

    @Override
    public void removeVote(Long blogId, Long voteId) {

        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        Blog originalBlog = null;

        if (optionalBlog.isPresent()) {
            originalBlog = optionalBlog.get();

            originalBlog.removeVote(voteId);
            this.saveBlog(originalBlog);
        }
    }

    @Override
    public Page<Blog> listBlogsByCatalog(Catalog catalog, Pageable pageable) {
        Page<Blog> blogs = blogRepository.findByCatalog(catalog, pageable);
        return blogs;
    }
}










