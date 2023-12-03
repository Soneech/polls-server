package org.soneech.service;

import org.soneech.model.Vote;
import org.soneech.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public Optional<Vote> findVoteByAnswerIdAndUserId(Long answerId, Long userId) {
        return voteRepository.findVoteByAnswerIdAndUserId(answerId, userId);
    }

    public Optional<Vote> findUserVoteInThisPoll(Long pollId, Long userId) {
        return voteRepository.findUserVoteInThisPoll(pollId, userId);
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }
}
