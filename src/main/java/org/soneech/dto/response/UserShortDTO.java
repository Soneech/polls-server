package org.soneech.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShortDTO implements UserDTO {
    private Long id;
    private String name;
}
