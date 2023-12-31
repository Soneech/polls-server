package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.exception.UserNotFoundException;
import org.soneech.model.User;
import org.soneech.repository.RoleRepository;
import org.soneech.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PollService pollService;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);
        if (foundUser.isEmpty())
            throw new UserNotFoundException();

        foundUser.get().setPollsInWhichVoted(pollService.findPollsInWhichUserVoted(id));
        return foundUser.get();
    }

    public Optional<User> findByEmail(String email) {  // for validation
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByName(String name) {  // for validation
        return userRepository.findByName(name);
    }

    @Transactional
    public User register(User user) {
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER").get()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
