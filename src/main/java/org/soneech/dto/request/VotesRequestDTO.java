package org.soneech.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VotesRequestDTO {
    @JsonProperty("poll_id")
    private Long pollId;

    @JsonProperty("votes")
    List<VoteRequestDTO> voteRequestDTOS;
}
