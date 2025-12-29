package Mappers;

import com.ecomerceproject.personalproject.DTOs.CarritoDTO;
import com.ecomerceproject.personalproject.Model.Carrito;

public class CarritoMapper {
    public static Carrito toEntity(CarritoDTO carritoDto) {
        return Carrito.builder().id(carritoDto.id()).products(carritoDto.products()).build();
    }

    public static CarritoDTO toDTO(Carrito entity) {
        return CarritoDTO.builder().id(entity.getId()).products(entity.getProducts()).build();
    }
}
