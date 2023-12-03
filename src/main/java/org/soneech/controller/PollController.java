package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.request.PollRequestDTO;
import org.soneech.dto.request.VoteRequestDTO;
import org.soneech.dto.response.PollDTO;
import org.soneech.dto.response.PollPreviewDTO;
import org.soneech.exception.PollException;
import org.soneech.exception.PollNotFoundException;
import org.soneech.exception.VoteDuplicateException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.Poll;
import org.soneech.model.User;
import org.soneech.model.Vote;
import org.soneech.service.AnswerService;
import org.soneech.service.PollService;
import org.soneech.service.UserService;
import org.soneech.service.VoteService;
import org.soneech.util.ErrorsUtil;
import org.soneech.util.VoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polls")
public class PollController {
    private final PollService pollService;
    private final AnswerService answerService;
    private final DefaultMapper mapper;
    private final UserService userService;
    private final VoteValidator voteValidator;
    private final VoteService voteService;
    private final ErrorsUtil errorsUtil;

    @Autowired
    public PollController(PollService pollService, AnswerService answerService, DefaultMapper mapper,
                          UserService userService, VoteValidator voteValidator,
                          VoteService voteService, ErrorsUtil errorsUtil) {
        this.pollService = pollService;
        this.answerService = answerService;
        this.mapper = mapper;
        this.userService = userService;
        this.voteValidator = voteValidator;
        this.voteService = voteService;
        this.errorsUtil = errorsUtil;
    }

    @GetMapping
    public ResponseEntity<List<PollDTO>> getAllPolls() {
        return ResponseEntity.ok(pollService.findAll().stream().map(mapper::convertToPollDTO).toList());
    }

    @GetMapping("/preview")
    public ResponseEntity<List<PollPreviewDTO>> getAllPreviewPollsInfo() {
        return ResponseEntity.ok(pollService.findAll().stream().map(mapper::convertToPollPreviewDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDTO> getPoll(@PathVariable("id") Long id) throws PollNotFoundException {
        return ResponseEntity.ok(mapper.convertToPollDTO(pollService.findById(id)));
    }

    @PostMapping("/vote")
    public ResponseEntity<Map<String, String>> vote(@RequestBody VoteRequestDTO voteRequestDTO)
                                                                        throws VoteDuplicateException {
        User user = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        voteValidator.validateVoteData(voteRequestDTO.getAnswerId(), user.getId(), voteRequestDTO.getPollId());

        Vote vote = mapper.convertToVote(voteRequestDTO, user);
        voteService.save(vote);
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
        answerService.saveAll(poll.getAnswers());

        return ResponseEntity.ok(Map.of("message", "Опрос успешно создан"));
    }
}
