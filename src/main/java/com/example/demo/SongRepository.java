package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import java.util.Optional;


public interface SongRepository extends Repository<Song, Long> {
    Page<Song> findAll(Pageable pageable);
    // @Query("SELECT s FROM Song s WHERE s.id = :id")
    Optional<SongWithoutAudio> findById(Long id);
    Optional<Song> findWithAudioById(Long id);
    SongWithoutAudio save(SongWithoutAudio newSong);
    Song save(Song newSong);
    void deleteById(Long id);

}

interface SongWithoutAudio {
    public String getArtist();
    public String getGenre();
    public Long getId();
    public int getLength();
    public String getTitle();
}