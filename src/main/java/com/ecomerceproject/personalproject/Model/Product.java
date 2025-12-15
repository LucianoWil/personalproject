package com.ecomerceproject.personalproject.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private double price;
    @Setter
    private String description;
    @Setter
    private Long categoryId;
    @Setter
    private int stock;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category='" + categoryId + '\'' +
                ", stock=" + stock +
                '}';
    }
}
