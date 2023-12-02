package org.soneech.dto;

import lombok.Getter;
import lombok.Setter;
import org.soneech.model.Role;

import java.util.List;

@Getter
@Setter
public class UserInfoDTO implements UserDTO {
    private Long id;
    private String name;
    private String email;
    private List<Role> roles;
}
