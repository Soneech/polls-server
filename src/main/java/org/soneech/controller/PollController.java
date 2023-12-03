package org.soneech.controller;

import org.soneech.dto.request.VoteRequestDTO;
import org.soneech.dto.response.PollDTO;
import org.soneech.dto.response.PollPreviewDTO;
import org.soneech.exception.PollNotFoundException;
import org.soneech.exception.VoteDuplicateException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.model.Vote;
import org.soneech.service.PollService;
import org.soneech.service.UserService;
import org.soneech.service.VoteService;
import org.soneech.util.VoteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/polls")
public class PollController {
    private final PollService pollService;
    private final DefaultMapper mapper;
    private final UserService userService;
    private final VoteValidator voteValidator;
    private final VoteService voteService;

    @Autowired
    public PollController(PollService pollService, DefaultMapper mapper,
                          UserService userService, VoteValidator voteValidator, VoteService voteService) {
        this.pollService = pollService;
        this.mapper = mapper;
        this.userService = userService;
        this.voteValidator = voteValidator;
        this.voteService = voteService;
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
    public ResponseEntity<Map<String, String>> vote(@RequestBody VoteRequestDTO voteRequestDTO,
                                                    @RequestHeader("Authorization") String jwt) throws VoteDuplicateException {
        User user = userService.getUserByClaimsInJWT(jwt.substring(7));
        voteValidator.validateVoteData(voteRequestDTO.getAnswerId(), user.getId(), voteRequestDTO.getPollId());

        Vote vote = mapper.convertToVote(voteRequestDTO, user);
        voteService.save(vote);
        return ResponseEntity.ok(Map.of("message", "Вы успешно проголосовали"));
    }
}
