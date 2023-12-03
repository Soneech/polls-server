package org.soneech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteRequestDTO {
    @JsonProperty("answer_id")
    private Long answerId;

    @JsonProperty("poll_id")
    private Long pollId;
}
