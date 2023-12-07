package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.model.Vote;
import org.soneech.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    public List<Vote> findUserVotesInThisPoll(Long pollId, Long userId) {
        return voteRepository.findUserVotesInThisPoll(pollId, userId);
    }

    public Vote save(Vote vote) {
        return voteRepository.save(vote);
    }

    public void saveAll(List<Vote> votes) {
        voteRepository.saveAll(votes);
    }
}
