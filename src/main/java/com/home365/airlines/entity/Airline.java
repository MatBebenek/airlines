package com.home365.airlines.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(callSuper = true, exclude = {"aircraftList"})
@Entity
@Table(name = "airlines")
@DynamicInsert
@DynamicUpdate
@ToString(callSuper = true, exclude = {"aircraftList"})
public class Airline extends BaseEntity {

    @Column(name = "name", nullable = false, length = 64)
    String airlineName;

    @Column(name = "budget")
    BigDecimal budget;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "homeBase_id")
    Destination homeBase;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    List<Aircraft> aircraftList = new ArrayList<>();

    public Airline(String airlineName, BigDecimal budget, Destination homeBase) {
        this.airlineName = airlineName;
        this.budget = budget;
        this.homeBase = homeBase;
    }

    public void increaseBudget(BigDecimal amount) {
        this.budget = budget.add(amount);
    }

    public void decreaseBudget(BigDecimal amount) {
        this.budget = budget.subtract(amount);
    }
}
