package org.soneech.util;

import org.soneech.exception.VoteDuplicateException;
import org.soneech.model.Vote;
import org.soneech.service.VoteService;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VoteValidator {
    private final VoteService voteService;

    public VoteValidator(VoteService voteService) {
        this.voteService = voteService;
    }

    public void validateVoteData(Long answerId, Long userId, Long pollId) {
        Optional<Vote> foundVoteByAnswerAndUser = voteService.findVoteByAnswerIdAndUserId(answerId, userId);
        Optional<Vote> foundUserVoteInThisPoll = voteService.findUserVoteInThisPoll(pollId, userId);
        if (foundVoteByAnswerAndUser.isPresent() || foundUserVoteInThisPoll.isPresent())
            throw new VoteDuplicateException();
    }
}
