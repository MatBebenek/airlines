package com.home365.airlines.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "destinations")
@Data
public class Destination {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Location location;

    @OneToOne(mappedBy = "homeBase")
    private Airline airline;

    public Destination(String name, Location location) {
        this.name = name;
        this.location = location;
    }
}
