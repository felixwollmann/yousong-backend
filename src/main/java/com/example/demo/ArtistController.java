package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping("/api/artists")
    public Iterable<Artist> allArtist() {
        return artistRepository.findAll();
    }

    @PostMapping("/api/artists")
    public Artist createArtist(@RequestBody Artist newArtist) {
        return artistRepository.save(newArtist);
    }

    @PatchMapping("/api/artists/{artistId}")
    public Artist updateArtist(@PathVariable Long artistId, @RequestBody Artist updatedArtist) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (artist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (artist.get().getId() != updatedArtist.getId()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return artistRepository.save(updatedArtist);
    }

    @DeleteMapping("/api/artists/{artistId}")
    public void deleteArtist(@PathVariable Long artistId) {
        var artist = artistRepository.findById(artistId);
        if (artist.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        artistRepository.deleteById(artist.get().getId());
    }
    

}
