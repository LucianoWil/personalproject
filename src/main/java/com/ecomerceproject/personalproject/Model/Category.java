package com.ecomerceproject.personalproject.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="categories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    @Setter
    private List<Product> products;
    @Setter
    private String description;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products.size() +
                ", description='" + description + '\'' +
                '}';
    }
}

