package org.soneech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PollRequestDTO {
    @NotBlank(message = "Тема не может быть пустой")
    @Size(min = 3, max = 255, message = "Текст темы должен содержать от 3 до 255 символов")
    private String theme;

    @JsonProperty("answers")
    @NotNull
    @Valid
    private List<AnswerRequestDTO> answerRequestDTOS;
}
