package com.home365.airlines.model;

import io.swagger.annotations.ApiModelProperty;
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
    private String name;

    @Column(name = "budget")
    private Double budget;

    @OneToOne
    @JoinColumn(name="homeBase_id")
    private Destination homeBase;

    @OneToMany(mappedBy = "owner")
    private List<Aircraft> aircraftList = new ArrayList<>();

    public Airline() {
    }

    public Airline(String name, Double budget, Destination homeBase) {
        this.name = name;
        this.budget = budget;
        this.homeBase = homeBase;
    }
}
