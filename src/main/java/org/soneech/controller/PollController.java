package org.soneech.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soneech.dto.request.PollRequestDTO;
import org.soneech.dto.request.VotesRequestDTO;
import org.soneech.dto.response.PollDTO;
import org.soneech.dto.response.PollPreviewDTO;
import org.soneech.exception.IncompleteAnswerOnPollException;
import org.soneech.exception.PollException;
import org.soneech.exception.PollNotFoundException;
import org.soneech.exception.VoteDuplicateException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Poll;
import org.soneech.model.User;
import org.soneech.model.Vote;
import org.soneech.service.PollService;
import org.soneech.service.UserService;
import org.soneech.service.VoteService;
import org.soneech.util.ErrorsUtil;
import org.soneech.util.VoteValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;
    private final DefaultMapper mapper;
    private final UserService userService;
    private final VoteValidator voteValidator;
    private final VoteService voteService;
    private final ErrorsUtil errorsUtil;

    @GetMapping("/preview")
    public ResponseEntity<List<PollPreviewDTO>> getAllPreviewPollsInfo() {
        return ResponseEntity.ok(pollService.findAll().stream().map(mapper::convertToPollPreviewDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDTO> getPoll(@PathVariable("id") Long id) throws PollNotFoundException {
        return ResponseEntity.ok(mapper.convertToPollDTO(pollService.findById(id)));
    }

    @PostMapping("/vote")
    public ResponseEntity<Map<String, String>> vote(@RequestBody VotesRequestDTO votesRequestDTO)
                                                                        throws VoteDuplicateException,
                                                                                IncompleteAnswerOnPollException {
        User user = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        voteValidator.validateVotes(votesRequestDTO, user);

        List<Vote> votes = votesRequestDTO.getVoteRequestDTOS()
                .stream().map(v -> mapper.convertToVote(v, user)).toList();

        voteService.saveAll(votes);
        return ResponseEntity.ok(Map.of("message", "Вы успешно проголосовали"));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> createPoll(@RequestBody @Valid PollRequestDTO pollRequestDTO,
                                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new PollException(errorsUtil.getFieldsErrorMessages(bindingResult));

        User user = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Poll poll = mapper.convertToPoll(pollRequestDTO, user);
        pollService.save(poll);

        return ResponseEntity.ok(Map.of("message", "Опрос успешно создан"));
    }
}
