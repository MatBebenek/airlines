package com.home365.airlines.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
public class Location {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "altitude", nullable = false)
    private Double altitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;
}
