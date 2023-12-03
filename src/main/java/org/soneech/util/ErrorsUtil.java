package org.soneech.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class ErrorsUtil {
    public String prepareFieldsErrorMessage(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var error: bindingResult.getFieldErrors()) {
            stringBuilder.append(error.getDefaultMessage()).append('\n');
        }
        return stringBuilder.toString();
    }
}
