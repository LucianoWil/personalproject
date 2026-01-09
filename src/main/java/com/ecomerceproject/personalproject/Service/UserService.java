package com.ecomerceproject.personalproject.Service;

import Mappers.UserMapper;
import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.Product;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Repository.ProductRepository;
import com.ecomerceproject.personalproject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        Optional <User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    public UserDTO createUser(UserDTO dto) {
        User user = UserMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.password()));
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserDTO update(Long id, UserDTO updatedDto) {
        Optional<User> optional = userRepository.findById(id);

        if(optional.isPresent()) {
            User user = optional.get();
            user.setUsername(updatedDto.username());
            user.setEmail(updatedDto.email());
            // Solo encriptamos si la contraseña ha cambiado y no es nula/vacía
            if (updatedDto.password() != null && !updatedDto.password().isEmpty()) {
                user.setPassword(passwordEncoder.encode(updatedDto.password()));
            }
            user.setRole(updatedDto.role());

            return UserMapper.toDTO(userRepository.save(user));
        }
        else{
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("User with id " + id + " not found");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found" + username));
        return (UserDetails) User.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).password(user.getPassword()).role(user.getRole()).build();

    }

    public void addProductToCart(Long userId, Long productId, int quantity){
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()){
            User user = optional.get();

            Optional<Product> optionalProduct = productRepository.findById(productId);

            if (optionalProduct.isPresent()){
                user.addProductToCart(optionalProduct.get(), quantity);
                userRepository.save(user);
            }
            else {
                throw new RuntimeException("Product with id " + productId + "not found");
            }

        }
        else {
            throw new RuntimeException("User with id " + userId + "not found");
        }
    }

    public void changeProductQuantity(Long userId, int quantity, Long productId){
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()){
            User user = optional.get();
            user.changeProductQuantity(productId, quantity);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User with id " + userId + "not found");
        }
    }

    public void deleteProduct(Long userId, Long productId){
        Optional<User> optional = userRepository.findById(userId);
        if (optional.isPresent()){
            User user = optional.get();
            user.deleteProduct(productId);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("User with id" + userId + "not found");
        }
    }
}
