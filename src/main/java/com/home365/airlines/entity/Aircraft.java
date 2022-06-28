package com.home365.airlines.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "aircrafts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true, exclude = "owner")
@ToString(callSuper = true, exclude = "owner")
public class Aircraft extends BaseEntity {

    @Column(name = "aircraft_name", nullable = false, length = 64)
    String aircraftName;

    @Column(name = "price", nullable = false)
    BigDecimal price;

    @Column(name = "max_distance", nullable = false, length = 64)
    BigDecimal maxDistance;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    @JsonIgnore
    Airline owner;

    public void setOwner(Airline airline) {
        this.owner = airline;
        airline.getAircraftList().add(this);
    }

    public void removeOwner(Airline airline) {
        this.owner = null;
        airline.getAircraftList().remove(this);
    }
}
