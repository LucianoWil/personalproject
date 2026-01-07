package com.ecomerceproject.personalproject.Service;

import Mappers.CartMapper;
import com.ecomerceproject.personalproject.DTOs.CartDTO;
import com.ecomerceproject.personalproject.Model.Cart;
import com.ecomerceproject.personalproject.Repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart getCarritoById(Long id) {
        if (cartRepository.findById(id).isPresent()) {
            return cartRepository.findById(id).get();
        }
        return null;
    }

    public CartDTO createCarrito(CartDTO dto) {
        Cart product = CartMapper.toEntity(dto);
        return CartMapper.toDTO(cartRepository.save(product));
    }

    public CartDTO update(Long id, CartDTO updatedDto) {
        Optional<Cart> optional = cartRepository.findById(id);

        if(optional.isPresent()) {
            Cart cart = optional.get();
            //cart.setItems(updatedDto.items());

            return CartMapper.toDTO(cartRepository.save(cart));
        }
        else{
            throw new RuntimeException("Carrito with id" + id + " not found");
        }
    }

    public void deleteCarrito(Long id) {
        if (cartRepository.existsById(id)) {
            cartRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Carrito with id" + id + " not found");
        }
    }
}
