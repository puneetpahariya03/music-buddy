package com.musicbuddy.service;

import com.musicbuddy.dto.request.ArtistRequest;
import com.musicbuddy.dto.response.ArtistResponse;
import com.musicbuddy.entity.Artist;
import com.musicbuddy.exception.ResourceNotFoundException;
import com.musicbuddy.repository.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Cacheable(value = "artists", key = "'all'")
    public List<ArtistResponse> getAllArtists() {
        return artistRepository.findAll().stream()
                .map(this::toResponse).collect(Collectors.toList());
    }

    @Cacheable(value = "artists", key = "'id-'+#id")
    public ArtistResponse getArtistById(Long id) {
        return toResponse(artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id)));
    }

    @Transactional
    @CacheEvict(value = "artists", allEntries = true)
    public ArtistResponse createArtist(ArtistRequest request) {
        Artist artist = Artist.builder()
                .name(request.getName())
                .bio(request.getBio())
                .imageUrl(request.getImageUrl())
                .build();
        return toResponse(artistRepository.save(artist));
    }

    @Transactional
    @CacheEvict(value = "artists", allEntries = true)
    public ArtistResponse updateArtist(Long id, ArtistRequest request) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist", id));
        artist.setName(request.getName());
        artist.setBio(request.getBio());
        artist.setImageUrl(request.getImageUrl());
        return toResponse(artistRepository.save(artist));
    }

    @Transactional
    @CacheEvict(value = "artists", allEntries = true)
    public void deleteArtist(Long id) {
        if (!artistRepository.existsById(id)) throw new ResourceNotFoundException("Artist", id);
        artistRepository.deleteById(id);
    }

    private ArtistResponse toResponse(Artist artist) {
        ArtistResponse r = new ArtistResponse();
        r.setId(artist.getId());
        r.setName(artist.getName());
        r.setBio(artist.getBio());
        r.setImageUrl(artist.getImageUrl());
        r.setSongCount(artist.getSongs() != null ? artist.getSongs().size() : 0);
        return r;
    }
}
