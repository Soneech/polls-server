package org.soneech.service;

import org.soneech.model.Answer;
import org.soneech.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    @Autowired
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer findById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }
}
