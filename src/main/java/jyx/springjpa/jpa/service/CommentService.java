package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Comment;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/28 21:49
 * @Version 1.0
 */
public interface CommentService {
    /**
     * 根据id获取Comment
     *
     * @param id
     * @return
     */
    Optional<Comment> getCommentById(Long id);

    /**
     * 删除评论
     *
     * @param id
     */
    void removeComment(Long id);
}
