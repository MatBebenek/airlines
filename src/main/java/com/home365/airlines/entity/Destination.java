package com.home365.airlines.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "destinations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true, exclude = {"homeBase"})
@ToString(callSuper = true, exclude = {"homeBase"})
public class Destination extends BaseEntity {
    @Column(name = "destination_name", nullable = false, length = 64, unique = true)
    String destinationName;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    Location location;

    @OneToOne(mappedBy = "homeBase")
    Airline homeBase;

    public Destination(String destinationName, Location location) {
        this.destinationName = destinationName;
        this.location = location;
    }
}
