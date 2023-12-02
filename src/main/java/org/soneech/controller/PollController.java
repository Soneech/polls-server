package org.soneech.controller;

import org.soneech.dto.response.PollDTO;
import org.soneech.dto.response.PollPreviewDTO;
import org.soneech.mapper.DefaultMapper;
import org.soneech.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/preview")
    public ResponseEntity<List<PollPreviewDTO>> getAllPreviewPollsInfo() {
        return ResponseEntity.ok(pollService.findAll().stream().map(mapper::convertToPollPreviewDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDTO> getPoll(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.convertToPollDTO(pollService.findById(id)));
    }
}
