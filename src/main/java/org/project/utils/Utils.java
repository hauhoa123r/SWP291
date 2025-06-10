package org.project.utils;


import org.project.dto.UserDTO;
import org.project.entity.UserEntity;

public class Utils {

    public static UserDTO mapUserEntityToUserDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;
    }
}
