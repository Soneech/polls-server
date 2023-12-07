package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.model.Question;
import org.soneech.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public List<Question> findByPollId(Long pollId) {
        return questionRepository.findByPollId(pollId);
    }

    public Question findByAnswerId(Long answerId) {
        return questionRepository.findByAnswerId(answerId);
    }

    public void saveAll(List<Question> questions) {
        questionRepository.saveAll(questions);
    }
}
