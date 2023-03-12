package com.onix.hcmustour.dto.mapper;


import com.onix.hcmustour.dto.model.UserDto;
import com.onix.hcmustour.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setMobileNumber(user.getMobileNumber())
                .setModel(user.getModel())
                .setRole(user.getRole());
    }
}
