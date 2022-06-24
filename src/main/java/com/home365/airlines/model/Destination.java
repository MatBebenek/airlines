package com.home365.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
public class Destination {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Location location;

    @OneToOne(mappedBy = "homeBase")
    @JsonIgnore
    private Airline airline;

    public Destination(String name, Location location) {
        this.name = name;
        this.location = location;
    }
}
