package com.chertov.coffeemachine.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CoffeeMachine")
public class CoffeeMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_portions")
    private int portionsLeft;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPortionsLeft() {
        return portionsLeft;
    }

    public void setPortionsLeft(int portionsLeft) {
        this.portionsLeft = portionsLeft;
    }
}
