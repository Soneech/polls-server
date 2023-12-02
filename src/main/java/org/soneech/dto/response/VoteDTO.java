package org.soneech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoteDTO {
    private Long id;

    @JsonProperty("user")
    private UserShortDTO userShortDTO;
}
