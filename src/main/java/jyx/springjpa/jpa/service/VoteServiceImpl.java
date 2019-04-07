package jyx.springjpa.jpa.service;

import jyx.springjpa.jpa.domain.Vote;
import jyx.springjpa.jpa.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author jyxlalala
 * @Date 2019/3/30 15:05
 * @Version 1.0
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Optional<Vote> getVoteById(Long id) {
        return voteRepository.findById(id);
    }

    @Override
    public void removeVote(Long id) {
        voteRepository.deleteById(id);
    }
}
