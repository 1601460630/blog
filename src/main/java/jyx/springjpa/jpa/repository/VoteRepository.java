package jyx.springjpa.jpa.repository;

import jyx.springjpa.jpa.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 15:02
 * @Version 1.0
 */
public interface VoteRepository extends JpaRepository<Vote,Long> {
}
