package org.soneech.controller;

import org.soneech.dto.PollDTO;
import org.soneech.mapper.DefaultMapper;
import org.soneech.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollController {
    private final PollService pollService;
    private final DefaultMapper mapper;

    @Autowired
    public PollController(PollService pollService, DefaultMapper mapper) {
        this.pollService = pollService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PollDTO>> getAllPolls() {
        return ResponseEntity.ok(pollService.findAll().stream().map(mapper::convertToPollDTO).toList());
    }
}
