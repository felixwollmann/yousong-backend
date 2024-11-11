package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SongController {
    @GetMapping("/hello")
    @CrossOrigin()
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/api/songs")
    public Iterable<Song> allSongs() {
        return songRepository.findAll();
    }

    @PostMapping("/api/songs")
    public Song createSong(@RequestBody Song newSong) {
        return songRepository.save(newSong);
    }

    @Autowired
    private SongRepository songRepository;
}
