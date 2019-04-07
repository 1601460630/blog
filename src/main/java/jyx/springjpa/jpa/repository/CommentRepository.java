package jyx.springjpa.jpa.repository;

import jyx.springjpa.jpa.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author jyxlalala
 * @Date 2019/3/28 21:47
 * @Version 1.0
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {
}
