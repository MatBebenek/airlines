package com.home365.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

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
    @JsonIgnore
    private Airline owner;

    @Column(name = "createdAt")
    private LocalDate createdAt;
    public void setOwner(Airline airline){
        this.owner = airline;
        airline.getAircraftList().add(this);
    }

    public void removeOwner(Airline airline){
        this.owner = null;
        airline.getAircraftList().remove(this);
    }
}
