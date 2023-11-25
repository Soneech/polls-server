package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.AuthenticationDTO;
import org.soneech.dto.RegistrationDTO;
import org.soneech.dto.UserDTO;
import org.soneech.exception.AuthException;
import org.soneech.mapper.DefaultMapper;
import org.soneech.model.User;
import org.soneech.security.JWTUtil;
import org.soneech.service.UserService;
import org.soneech.util.ErrorsUtil;
import org.soneech.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final DefaultMapper mapper;
    private final UserValidator userValidator;
    private final ErrorsUtil errorsUtil;
    private final UserService userService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(DefaultMapper mapper, UserValidator userValidator,
                          ErrorsUtil errorsUtil, UserService userService, JWTUtil jwtUtil,
                          AuthenticationManager authenticationManager) {
        this.mapper = mapper;
        this.userValidator = userValidator;
        this.errorsUtil = errorsUtil;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid RegistrationDTO registrationDTO,
                                            BindingResult bindingResult) {
        User user = mapper.convertToUser(registrationDTO);
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            throw new AuthException(HttpStatus.BAD_REQUEST, errorsUtil.prepareFieldsErrorMessage(bindingResult));

        User savedUser = userService.register(user);
        return new ResponseEntity<>(mapper.convertToUserDTO(savedUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthenticationDTO authenticationDTO)
                                                    throws AuthenticationException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        authenticationDTO.getName(),
                        authenticationDTO.getPassword()
                );
        try {
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException exception) {
            throw new AuthException(HttpStatus.BAD_REQUEST, "Неверный логин или пароль");
        }

        String jwt = jwtUtil.generateToken(authenticationDTO.getName());
        return new ResponseEntity<>(Map.of("jwt", jwt), HttpStatus.OK);
    }
}
