package com.example.demo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    private String artist;

    private String genre;

    private int length;

    protected Song() {
        // no-args constructor required by JPA spec
        // this one is protected since it should not be used directly
    }

    public Song(String title, String artist, String genre, int length) {
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.length = length;
    }

    public String getArtist() {
        return artist;
    }

    public String getGenre() {
        return genre;
    }

    public Long getId() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public String getTitle() {
        return title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
