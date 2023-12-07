package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.model.Answer;
import org.soneech.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public void saveAll(List<Answer> answers) {
        answerRepository.saveAll(answers);
    }
}
