package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @PatchMapping("/api/songs/{songId}")
    public Song updateSong(@PathVariable Long songId, @RequestBody Song updatedSong) {
        Optional<Song> song = songRepository.findById(songId);
        if (song.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found");
        }
        if (song.get().getId() != updatedSong.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return songRepository.save(updatedSong);
        // return song.get();
    }

    @DeleteMapping("/api/songs/{songId}")
    public void deleteSong(@PathVariable Long songId) {
        var song = songRepository.findById(songId);
        if (song.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found");
        }
        songRepository.deleteById(song.get().getId());
    }

    @Autowired
    private SongRepository songRepository;
}
