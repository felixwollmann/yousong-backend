package com.example.demo;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class SongController {
    @GetMapping("/hello")
    @CrossOrigin()
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/api/songs")
    public Page<SongWithoutAudio> allSongs(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "q", defaultValue = "") String q,
            @RequestParam(name = "genres", required = false) Set<String> genres) {
        final var PAGE_SIZE = 5;
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE);
        if (q.equals("") && (genres == null || genres.isEmpty()))
            return songRepository.findAll(pageRequest);
        else
            return songRepository.findBySearchText(q, genres, pageRequest);
    }

    @PostMapping("/api/songs/{songId}/audio")
    public void uploadSong(@PathVariable Long songId, @RequestParam("file") MultipartFile file) {
        Optional<Song> song = songRepository.findWithAudioById(songId);
        if (song.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Song not found");
        }
        try {
            var updatedSong = song.get();
            updatedSong.setAudio(file.getBytes());
            songRepository.save(updatedSong);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error handling file");
        }
    }

    @GetMapping("/api/songs/{songId}/audio")
    public ResponseEntity<Resource> getAudio(@PathVariable Long songId) {
        Optional<Song> song = songRepository.findWithAudioById(songId);
        if (song.isEmpty() || song.get().getAudio() == null) {
            return ResponseEntity.notFound().build();
        }
        Resource file = new ByteArrayResource(song.get().getAudio());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/api/songs")
    public Song createSong(@RequestBody Song newSong) {
        return songRepository.save(newSong);
    }

    @PatchMapping("/api/songs/{songId}")
    public Song updateSong(@PathVariable Long songId, @RequestBody Song updatedSong) {
        Optional<Song> song = songRepository.findWithAudioById(songId);
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
