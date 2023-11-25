package org.soneech.controller;

import org.soneech.dto.UserDTO;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final DefaultMapper defaultMapper;

    @Autowired
    public UserController(UserService userService, DefaultMapper defaultMapper) {
        this.userService = userService;
        this.defaultMapper = defaultMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = users.stream().map(defaultMapper::convertToUserDTO).toList();
        return ResponseEntity.ok(usersDTO);
    }
}
