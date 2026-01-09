package com.ecomerceproject.personalproject.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
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

    private boolean isFeatured;
    
    @Setter
    @ManyToOne
    @JoinColumn(name="category")
    private Category category;
    
    @Setter
    private int stock;

    @Setter
    private String imageUrl;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", category ='" + category.getName() + '\'' +
                ", stock=" + stock +
                '}';
    }
}
