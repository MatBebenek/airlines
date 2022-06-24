package com.home365.airlines.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "aircrafts")
@Data
@NoArgsConstructor
public class Aircraft {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "max_distance", nullable = false, length = 64)
    private Double maxDistance;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Airline owner;
}
