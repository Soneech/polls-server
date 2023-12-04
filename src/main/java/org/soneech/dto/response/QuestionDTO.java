package org.soneech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class QuestionDTO {
    private Long id;
    private String text;

    @JsonProperty("answers")
    private List<AnswerDTO> answerDTOS = new ArrayList<>();
}
