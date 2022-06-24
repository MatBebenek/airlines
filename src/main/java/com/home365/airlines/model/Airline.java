package com.home365.airlines.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "airlines")
@Data
public class Airline {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String airlineName;

    @Column(name = "budget")
    private Double budget;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "homeBase_id")
    private Destination homeBase;

    @OneToMany(mappedBy = "owner")
    private List<Aircraft> aircraftList = new ArrayList<>();

    public Airline() {
    }

    public Airline(String airlineName, Double budget, Destination homeBase) {
        this.airlineName = airlineName;
        this.budget = budget;
        this.homeBase = homeBase;
    }

    public void increaseBudget(Double amount) {
        this.budget += amount;
    }

    public void decreaseBudget(Double amount) {
        this.budget -= amount;
    }
}
