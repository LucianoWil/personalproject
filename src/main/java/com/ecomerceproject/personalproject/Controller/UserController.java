package com.ecomerceproject.personalproject.Controller;

import com.ecomerceproject.personalproject.DTOs.UserDTO;
import com.ecomerceproject.personalproject.Model.User;
import com.ecomerceproject.personalproject.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedDto) {
        return ResponseEntity.ok(userService.update(id, updatedDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addProduct/{userId}")
    public void addProduct(@RequestBody Map<String, Object> data, @PathVariable long userId){
        Long productId = Long.valueOf(data.get("productId").toString());
        int quantity = Integer.parseInt(data.get("quantity").toString());
        userService.addProductToCart(userId, productId, quantity);
    }

    @PatchMapping("/changeProductQuantity/{userId}")
    public void changeProductQuantity(@RequestBody Map<String, Object> data, @PathVariable long userId){
        int quantity = Integer.parseInt(data.get("quantity").toString());
        Long productId = Long.valueOf(data.get("productId").toString());
        userService.changeProductQuantity(userId, quantity, productId);
    }

    @DeleteMapping("/deleteProduct/{userId}")
    public void deleteProduct(@RequestBody Map<String, Object> data, @PathVariable long userId){
        Long productId = Long.valueOf(data.get("productId").toString());
        userService.deleteProduct(userId, productId);
    }
}
