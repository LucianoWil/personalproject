package com.ecomerceproject.personalproject.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name="users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    private String role = "user";
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<CartItem> cart = new ArrayList<>();

    public void setAdmin(boolean admin) {
       if (admin){
           role = "ADMIN";
        }
    }

    public void addProductToCart(Product product, int quantity){
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = CartItem.builder()
                    .product(product)
                    .quantity(quantity)
                    .build();
            cart.add(newItem);
        }
    }

    public void changeProductQuantity(Long productId, int quantity){
        Optional<CartItem> existingItem = cart.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
        }
        else{
            throw new RuntimeException("Product with id " + productId + "not found");
        }
    }

    public void deleteProduct(Long productId){
        Optional<CartItem> existingItem = cart.stream().filter(item -> item.getProduct().getId().equals(productId)).findFirst();
        if (existingItem.isPresent()){
            CartItem item = existingItem.get();
            cart.remove(item);
        }
        else{
            throw new RuntimeException("Product with id " + productId + "not found");
        }
    }
}
