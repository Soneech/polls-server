package org.soneech.service;

import org.soneech.exception.PollNotFoundException;
import org.soneech.model.Poll;
import org.soneech.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {
    private final PollRepository pollRepository;

    @Autowired
    public PollService(PollRepository pollRepository) {
        this.pollRepository = pollRepository;
    }

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

    public Poll save(Poll poll) {
        poll.setCreatedAt(LocalDateTime.now());
        return pollRepository.save(poll);
    }
}
