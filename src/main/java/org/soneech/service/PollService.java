package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.exception.PollNotFoundException;
import org.soneech.model.Poll;
import org.soneech.model.Question;
import org.soneech.repository.PollRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public List<Poll> findAll() {
        return pollRepository.findAll();
    }

    public Poll findById(Long id) {
        Optional<Poll> foundPoll = pollRepository.findById(id);
        if (foundPoll.isEmpty())
            throw new PollNotFoundException();
        return foundPoll.get();
    }

    public List<Poll> findPollsInWhichUserVoted(Long userId) {
        return pollRepository.findPollsInWhichUserVoted(userId);
    }

    public void save(Poll poll) {
        poll.setCreatedAt(LocalDateTime.now());
        pollRepository.save(poll);

        List<Question> questions = poll.getQuestions();
        questionService.saveAll(questions);

        for (Question question: questions) {
            answerService.saveAll(question.getAnswers());
        }
    }
}
