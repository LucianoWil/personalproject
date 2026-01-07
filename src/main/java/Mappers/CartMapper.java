package Mappers;

import com.ecomerceproject.personalproject.DTOs.CartDTO;
import com.ecomerceproject.personalproject.DTOs.CartItemDTO;
import com.ecomerceproject.personalproject.Model.Cart;
import com.ecomerceproject.personalproject.Model.CartItem;

import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {
    public static Cart toEntity(CartDTO cartDto) {
        List<CartItem> items = cartDto.items().stream()
                .map(itemDto -> CartItem.builder()
                        .id(itemDto.id())
                        .product(itemDto.product())
                        .quantity(itemDto.quantity())
                        .build())
                .collect(Collectors.toList());

        return Cart.builder()
                .id(cartDto.id())
                .items(items)
                .user(cartDto.user())
                .build();
    }

    public static CartDTO toDTO(Cart entity) {
        List<CartItemDTO> itemDTOs = entity.getItems().stream()
                .map(item -> CartItemDTO.builder()
                        .id(item.getId())
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CartDTO.builder()
                .id(entity.getId())
                .items(itemDTOs)
                .user(entity.getUser())
                .build();
    }
}
