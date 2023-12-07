package org.soneech.controller;

import lombok.RequiredArgsConstructor;
import org.soneech.dto.response.UserDTO;
import org.soneech.dto.response.UserPublicInfoDTO;
import org.soneech.exception.UserNotFoundException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DefaultMapper defaultMapper;

    @GetMapping
    public ResponseEntity<List<UserPublicInfoDTO>> getAllUsers() {
        List<User> users = userService.findAll();

        List<UserPublicInfoDTO> publicInfoDTOS =
                users.stream().map(defaultMapper::convertToUserPublicInfoDTO).toList();
        return ResponseEntity.ok(publicInfoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserInfo(@PathVariable("id") Long id) throws UserNotFoundException {
        User foundUser = userService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDTO userDTO;
        if (foundUser.getName().equals(authentication.getName()))
            userDTO = defaultMapper.convertToUserInfoDTO(foundUser);
        else
            userDTO = defaultMapper.convertToUserPublicInfoDTO(foundUser);

        return ResponseEntity.ok(userDTO);
    }
}
