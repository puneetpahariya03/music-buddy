package com.musicbuddy.repository;

import com.musicbuddy.entity.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s JOIN s.artist a " +
           "WHERE LOWER(s.title) LIKE LOWER(CONCAT('%',:q,'%')) " +
           "OR LOWER(a.name) LIKE LOWER(CONCAT('%',:q,'%'))")
    Page<Song> search(@Param("q") String query, Pageable pageable);

    List<Song> findByArtistId(Long artistId);
    List<Song> findByAlbumId(Long albumId);
    List<Song> findByGenre(String genre);
}
