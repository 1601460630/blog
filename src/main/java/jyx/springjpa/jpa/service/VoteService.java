package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Vote;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 15:03
 * @Version 1.0
 */
public interface VoteService {
    /**
     * 根据Id获取vote
     * @param id
     * @return
     */
    Optional<Vote> getVoteById(Long id);

    /**
     * 根据Id获取vote
     * @param id
     */
    void removeVote(Long id);
}
