package org.soneech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnswerDTO {
    private Long id;
    private String text;

    @JsonProperty("votes")
    private List<VoteDTO> voteDTOS = new ArrayList<>();
}
