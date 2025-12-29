package Mappers;

import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.User;

public class UserMapper {
    public static User toEntity(UserDTO userDTO) {
        return User.builder().id(userDTO.id()).username(userDTO.username()).email(userDTO.email()).password(userDTO.password()).role(userDTO.role()).build();
    }

    public static UserDTO toDTO(User entity) {
        return UserDTO.builder().id(entity.getId()).username(entity.getUsername()).email(entity.getEmail()).password(entity.getPassword()).role(entity.getRole()).build();
    }
}
