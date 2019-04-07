package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Comment;
import jyx.springjpa.jpa.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/28 21:50
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public Optional<Comment> getCommentById(Long id) {

        return commentRepository.findById(id);
    }

    @Override
    public void removeComment(Long id) {

        commentRepository.deleteById(id);
    }
}
