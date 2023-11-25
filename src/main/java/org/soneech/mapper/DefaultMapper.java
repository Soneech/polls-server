package org.soneech.mapper;

import org.modelmapper.ModelMapper;
import org.soneech.dto.RegistrationDTO;
import org.soneech.dto.UserDTO;
import org.soneech.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public DefaultMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User convertToUser(RegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
