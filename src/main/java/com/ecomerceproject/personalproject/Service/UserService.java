package com.ecomerceproject.personalproject.Service;

import Mappers.UserMapper;
import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        if (userRepository.existsById(id)) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public UserDTO createUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO updatedDto) {
        Optional<User> optional = userRepository.findById(id);

        if(optional.isPresent()) {
            User user = optional.get();
            user.setName(updatedDto.name());
            user.setEmail(updatedDto.email());
            user.setPassword(updatedDto.password());
            user.setAdmin(updatedDto.admin());

            return UserMapper.toDTO(userRepository.save(user));
        }
        else{
            throw new RuntimeException("User with id" + id + " not found");
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("User with id" + id + " not found");
        }
    }
}
