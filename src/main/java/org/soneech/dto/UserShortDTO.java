package org.soneech.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserShortDTO implements UserDTO {
    private Long id;
    private String name;
}
