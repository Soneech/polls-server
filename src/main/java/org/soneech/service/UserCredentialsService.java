package org.soneech.service;

import lombok.RequiredArgsConstructor;
import org.soneech.model.User;
import org.soneech.repository.UserRepository;
import org.soneech.security.UserCredentials;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCredentialsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userRepository.findByName(username);
        if (foundUser.isEmpty())
            throw new UsernameNotFoundException("Пользователь с таким именем не найден");
        return new UserCredentials(foundUser.get());
    }
}
