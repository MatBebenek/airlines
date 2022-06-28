package com.home365.airlines.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
@DynamicUpdate
@FieldDefaults(level = PRIVATE)
public class Location extends BaseEntity {
    @Column(name = "latitude", nullable = false)
    BigDecimal latitude;

    @Column(name = "longitude", nullable = false)
    BigDecimal longitude;
}
