package org.soneech.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.soneech.model.Role;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserPublicInfoDTO implements UserDTO {
    private Long id;
    private String name;
    private List<Role> roles;
}
