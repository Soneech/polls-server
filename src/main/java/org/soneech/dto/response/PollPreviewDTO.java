package org.soneech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PollPreviewDTO {
    private Long id;
    private String theme;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("user")
    private UserShortDTO userShortDTO;
}
