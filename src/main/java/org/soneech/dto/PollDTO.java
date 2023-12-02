package org.soneech.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PollDTO {
    private Long id;
    private String theme;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("user")
    private UserShortDTO userShortDTO;

    @JsonProperty("answers")
    private List<AnswerDTO> answerDTOS = new ArrayList<>();
}
