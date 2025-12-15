package Mappers;

import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.User;

public class UserMapper {
    public static User toEntity(UserDTO userDTO) {
        return User.builder().id(userDTO.id()).name(userDTO.name()).email(userDTO.email()).password(userDTO.password()).admin(userDTO.admin()).build();
    }

    public static UserDTO toDTO(User entity) {
        return UserDTO.builder().id(entity.getId()).name(entity.getName()).email(entity.getEmail()).password(entity.getPassword()).admin(entity.isAdmin()).build();
    }
}
