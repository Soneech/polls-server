package org.soneech.util;

import org.soneech.model.User;
import org.soneech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        Optional<User> foundUser = userService.findByEmail(user.getEmail());

        if (foundUser.isPresent() && foundUser.get().getId() != user.getId()) {
            errors.rejectValue("email", "", "Пользователь с такой почтой уже существует");
        }
    }
}
