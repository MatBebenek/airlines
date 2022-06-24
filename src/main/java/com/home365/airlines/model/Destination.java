package com.home365.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "destination_name", nullable = false, length = 64)
    private String destinationName;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private Location location;

    @OneToOne(mappedBy = "homeBase")
    @JsonIgnore
    private Airline homeBase;

    public Destination(String destinationName, Location location) {
        this.destinationName = destinationName;
        this.location = location;
    }
}
