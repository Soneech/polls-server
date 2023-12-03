package org.soneech.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ErrorsUtil {
    public List<String> getFieldsErrorMessages(BindingResult bindingResult) {
        Set<String> errors = new HashSet<>();
        for (var error: bindingResult.getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return errors.stream().toList();
    }
}
