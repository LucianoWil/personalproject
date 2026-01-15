package com.ecomerceproject.personalproject.Service;

import com.ecomerceproject.personalproject.Repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    @Autowired
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public void deleteCartItemByCategory(Long id){
        if (id != null){
            cartItemRepository.deleteByProductCategoryId(id);
        }
        else{
            throw new RuntimeException("Category with id" + id + " not found");
        }
    }

    public void deleteCartItemByProduct(Long id){
        if (id != null){
            cartItemRepository.deleteByProductId(id);
        }
        else {
            throw new RuntimeException("Product with id" + id + "not found");
        }
    }
}
