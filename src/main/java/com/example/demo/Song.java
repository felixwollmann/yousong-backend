package com.example.demo;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;

@Entity
public class Song {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;


    @ManyToOne()
    private Artist artist;

    // private String genre;
    // @CollectionTable(name = "user_phone_numbers", joinColumns = @JoinColumn(name = "user_id"))
    // @Column(name = "phone_number")
    // @ElementCollection
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> genres;
    
    private int length;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] audio;

    protected Song() {
        // no-args constructor required by JPA spec
        // this one is protected since it should not be used directly
    }

    public Song(String title, Artist artist, Set<String> genres, int length) {
        this.title = title;
        this.artist = artist;
        this.genres = genres;
        this.length = length;
    }

    public Artist getArtist() { 
        return artist;
    }

    public Set<String> getGenres() {
        return genres;
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

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getAudio() {
        return audio;
    }

    public void setAudio(byte[] audio) {
        this.audio = audio;
    }
}
