package org.soneech.service;

import org.soneech.exception.AuthException;
import org.soneech.model.User;
import org.soneech.repository.RoleRepository;
import org.soneech.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new AuthException(HttpStatus.NOT_FOUND, "Пользователь с таким id не найден");
        return foundUser.get();
    }

    public Optional<User> findByEmail(String email) {  // for validation
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User register(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER").get()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
