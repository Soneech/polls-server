package org.soneech.util;

import org.soneech.dto.request.VotesRequestDTO;
import org.soneech.exception.IncompleteAnswerOnPollException;
import org.soneech.exception.VoteDuplicateException;
import org.soneech.model.Question;
import org.soneech.model.User;
import org.soneech.model.Vote;
import org.soneech.service.QuestionService;
import org.soneech.service.VoteService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class VoteValidator {
    private final VoteService voteService;
    private final QuestionService questionService;

    public VoteValidator(VoteService voteService, QuestionService questionService) {
        this.voteService = voteService;
        this.questionService = questionService;
    }

    public void validateVotes(VotesRequestDTO votesRequestDTO, User user) {
        validateOnDuplicateVoting(user.getId(), votesRequestDTO.getPollId());
        validateCheckingAnswers(votesRequestDTO);

    }

    private void validateOnDuplicateVoting(Long userId, Long pollId) {
        List<Vote> foundUserVotes = voteService.findUserVotesInThisPoll(pollId, userId);
        if (!foundUserVotes.isEmpty())
            throw new VoteDuplicateException();
    }

    private void validateCheckingAnswers(VotesRequestDTO votesRequestDTO) {
        List<Question> questionsInThisPoll = questionService.findByPollId(votesRequestDTO.getPollId());

        Set<Question> answeredQuestions = new HashSet<>();
        for (var voteReqDTO: votesRequestDTO.getVoteRequestDTOS()) {
            Question question = questionService.findByAnswerId(voteReqDTO.getAnswerId());
            answeredQuestions.add(question);
        }

        if (questionsInThisPoll.size() != answeredQuestions.size())
            throw new IncompleteAnswerOnPollException();
    }
}
