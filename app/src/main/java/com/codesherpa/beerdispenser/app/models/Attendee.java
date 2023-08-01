package com.codesherpa.beerdispenser.app.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Attendee {

    @Id
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}