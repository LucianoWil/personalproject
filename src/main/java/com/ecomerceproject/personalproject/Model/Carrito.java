package com.ecomerceproject.personalproject.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="carritos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @Setter
    private List<Product> products;
    @Setter
    @OneToOne
    private User user;
}
