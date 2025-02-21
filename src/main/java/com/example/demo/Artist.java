package com.example.demo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Artist {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    @NotBlank()
    private String name;

    protected Artist() {}

    public Artist(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }
 }
