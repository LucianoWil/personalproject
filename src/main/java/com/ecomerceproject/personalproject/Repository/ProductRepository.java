package com.ecomerceproject.personalproject.Repository;

import com.ecomerceproject.personalproject.Model.Category;
import com.ecomerceproject.personalproject.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryName(String name);
}
