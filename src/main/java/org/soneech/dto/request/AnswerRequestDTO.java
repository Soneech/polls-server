package org.soneech.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AnswerRequestDTO {
    @NotBlank(message = "Вариант ответа не может быть пустым")
    @Size(min = 2, max = 100, message = "Вариант ответа должен содержать от 2 до 100 символов")
    private String text;
}
