package com.musicbuddy.service;

import com.musicbuddy.dto.request.SongRequest;
import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.entity.*;
import com.musicbuddy.exception.ResourceNotFoundException;
import com.musicbuddy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final FavoriteRepository favoriteRepository;

    @Cacheable(value = "songs", key = "'all-p'+#page+'-s'+#size")
    public Page<SongResponse> getAllSongs(int page, int size, Long currentUserId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("title"));
        return songRepository.findAll(pageable).map(s -> toResponse(s, currentUserId));
    }

    @Cacheable(value = "songs", key = "'search-'+#query+'-p'+#page+'-s'+#size")
    public Page<SongResponse> searchSongs(String query, int page, int size, Long currentUserId) {
        Pageable pageable = PageRequest.of(page, size);
        return songRepository.search(query, pageable).map(s -> toResponse(s, currentUserId));
    }

    @Cacheable(value = "songs", key = "'id-'+#id")
    public SongResponse getSongById(Long id, Long currentUserId) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", id));
        return toResponse(song, currentUserId);
    }

    @Transactional
    @CacheEvict(value = "songs", allEntries = true)
    public SongResponse createSong(SongRequest request) {
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", request.getArtistId()));
        Album album = request.getAlbumId() != null
                ? albumRepository.findById(request.getAlbumId()).orElse(null) : null;
        Song song = Song.builder()
                .title(request.getTitle())
                .durationSeconds(request.getDurationSeconds())
                .genre(request.getGenre())
                .audioUrl(request.getAudioUrl())
                .artist(artist)
                .album(album)
                .build();
        return toResponse(songRepository.save(song), null);
    }

    @Transactional
    @CacheEvict(value = "songs", allEntries = true)
    public SongResponse updateSong(Long id, SongRequest request) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Song", id));
        Artist artist = artistRepository.findById(request.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist", request.getArtistId()));
        song.setTitle(request.getTitle());
        song.setDurationSeconds(request.getDurationSeconds());
        song.setGenre(request.getGenre());
        song.setAudioUrl(request.getAudioUrl());
        song.setArtist(artist);
        if (request.getAlbumId() != null) {
            song.setAlbum(albumRepository.findById(request.getAlbumId()).orElse(null));
        }
        return toResponse(songRepository.save(song), null);
    }

    @Transactional
    @CacheEvict(value = "songs", allEntries = true)
    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) throw new ResourceNotFoundException("Song", id);
        songRepository.deleteById(id);
    }

    public SongResponse toResponse(Song song, Long currentUserId) {
        SongResponse r = new SongResponse();
        r.setId(song.getId());
        r.setTitle(song.getTitle());
        r.setDurationSeconds(song.getDurationSeconds());
        r.setGenre(song.getGenre());
        r.setAudioUrl(song.getAudioUrl());
        if (song.getArtist() != null) {
            r.setArtistName(song.getArtist().getName());
            r.setArtistId(song.getArtist().getId());
        }
        if (song.getAlbum() != null) {
            r.setAlbumTitle(song.getAlbum().getTitle());
            r.setAlbumId(song.getAlbum().getId());
            r.setAlbumCoverUrl(song.getAlbum().getCoverUrl());
        }
        if (currentUserId != null) {
            r.setFavorited(favoriteRepository.existsByUserIdAndSongId(currentUserId, song.getId()));
        }
        return r;
    }
}
