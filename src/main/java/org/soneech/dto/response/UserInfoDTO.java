package org.soneech.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.soneech.model.Role;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserInfoDTO implements UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;

    @JsonProperty("created_polls")
    private List<PollShortDTO> createdPolls = new ArrayList<>();

    @JsonProperty("polls_in_which_voted")
    private List<PollShortDTO> pollsInWhichVoted = new ArrayList<>();
}
