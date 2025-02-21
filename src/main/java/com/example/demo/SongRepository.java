package com.example.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


public interface SongRepository extends Repository<Song, Long> {
    Page<SongWithoutAudio> findAll(Pageable pageable);
    // @Query("SELECT s FROM Song s WHERE s.id = :id")
    Optional<SongWithoutAudio> findWithoutAudioById(Long id);
    Optional<Song> findWithAudioById(Long id);
    SongWithoutAudio save(SongWithoutAudio newSong);
    Song save(Song newSong);
    void deleteById(Long id);

    @Query("SELECT s FROM Song s LEFT JOIN s.genres g WHERE " +
            "(LOWER(s.artist.name) LIKE LOWER(CONCAT('%', :searchText, '%')) OR " +
            "LOWER(s.title) LIKE LOWER(CONCAT('%', :searchText, '%')))"+
            "AND (:genres IS NULL OR g IN :genres)")
    Page<SongWithoutAudio> findBySearchText(@Param("searchText") String searchText, @Param("genres") Set<String> genres, Pageable pageable);

}

interface SongWithoutAudio {
    public String getArtist();
    public Set<String> getGenres();
    public Long getId();
    public int getLength();
    public String getTitle();
}