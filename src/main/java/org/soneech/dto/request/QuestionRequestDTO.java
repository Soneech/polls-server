package org.soneech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionRequestDTO {
    @NotBlank(message = "Текст вопроса не может быть пустым")
    @Size(min = 2, max = 100, message = "Текст вопроса должен содержать от 2 до 100 символов")
    private String text;

    @JsonProperty("answers")
    @NotNull
    @Valid
    @Size(min = 2, message = "Для вопроса должно быть минимум 2 варианта ответа")
    private List<AnswerRequestDTO> answerRequestDTOS;
}
