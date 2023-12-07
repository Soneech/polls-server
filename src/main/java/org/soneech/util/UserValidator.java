package org.soneech.util;

import lombok.RequiredArgsConstructor;
import org.soneech.model.User;
import org.soneech.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> foundByEmail = userService.findByEmail(user.getEmail());

        if (foundByEmail.isPresent() && foundByEmail.get().getId() != user.getId()) {
            errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
        }

        Optional<User> foundByName = userService.findByName(user.getName());
        if (foundByName.isPresent() && foundByName.get().getId() != user.getId()) {
            errors.rejectValue("name", "", "Пользователь с таким именем уже существует");
        }
    }
}
