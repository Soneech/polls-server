package org.soneech.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soneech.dto.request.AuthenticationDTO;
import org.soneech.dto.request.RegistrationDTO;
import org.soneech.dto.response.UserInfoDTO;
import org.soneech.exception.AuthException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.security.JWTUtil;
import org.soneech.service.UserService;
import org.soneech.util.ErrorsUtil;
import org.soneech.util.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final DefaultMapper mapper;
    private final UserValidator userValidator;
    private final ErrorsUtil errorsUtil;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public ResponseEntity<UserInfoDTO> register(@RequestBody @Valid RegistrationDTO registrationDTO,
                                                BindingResult bindingResult) {
        User user = mapper.convertToUser(registrationDTO);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            throw new AuthException(errorsUtil.getFieldsErrorMessages(bindingResult));

        User savedUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.convertToUserInfoDTO(savedUser));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getName(),
                        authenticationDTO.getPassword()
                );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exception) {
            throw new AuthException(Collections.singletonList("Неверный логин или пароль"));
        }

        String jwt = jwtUtil.generateToken(authenticationDTO.getName());
        return ResponseEntity.ok(Map.of("token", jwt));
    }
}
