package com.ecomerceproject.personalproject.Service;

import Mappers.CarritoMapper;
import com.ecomerceproject.personalproject.DTOs.CarritoDTO;
import com.ecomerceproject.personalproject.Model.Carrito;
import com.ecomerceproject.personalproject.Repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {
    @Autowired
    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public Carrito getCarritoById(Long id) {
        if (carritoRepository.findById(id).isPresent()) {
            return carritoRepository.findById(id).get();
        }
        return null;
    }

    public CarritoDTO createCarrito(CarritoDTO dto) {
        Carrito product = CarritoMapper.toEntity(dto);
        return CarritoMapper.toDTO(carritoRepository.save(product));
    }

    public CarritoDTO update(Long id, CarritoDTO updatedDto) {
        Optional<Carrito> optional = carritoRepository.findById(id);

        if(optional.isPresent()) {
            Carrito carrito = optional.get();
            carrito.setProducts(updatedDto.products());

            return CarritoMapper.toDTO(carritoRepository.save(carrito));
        }
        else{
            throw new RuntimeException("Carrito with id" + id + " not found");
        }
    }

    public void deleteCarrito(Long id) {
        if (carritoRepository.existsById(id)) {
            carritoRepository.deleteById(id);
        }
        else{
            throw new RuntimeException("Carrito with id" + id + " not found");
        }
    }
}
