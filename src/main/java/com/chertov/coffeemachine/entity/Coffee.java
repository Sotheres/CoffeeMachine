package com.chertov.coffeemachine.entity;

import com.chertov.coffeemachine.typesofcoffee.CoffeeType;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coffee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CoffeeType type;

    @Column(name = "time_of_brewing")
    private Instant timeOfBrewing;

    public Coffee() {
    }

    public Coffee(Long id, CoffeeType type, Instant timeOfBrewing) {
        this.id = id;
        this.type = type;
        this.timeOfBrewing = timeOfBrewing;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoffeeType getType() {
        return type;
    }

    public void setType(CoffeeType type) {
        this.type = type;
    }

    public Instant getTimeOfBrewing() {
        return timeOfBrewing;
    }

    public void setTimeOfBrewing(Instant timeOfBrewing) {
        this.timeOfBrewing = timeOfBrewing;
    }
}
