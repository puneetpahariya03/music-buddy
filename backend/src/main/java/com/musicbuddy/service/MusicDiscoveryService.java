package com.musicbuddy.service;

import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.dto.response.iTunesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicDiscoveryService {

    private final RestTemplate restTemplate;

    private static final String ITUNES_API_URL = "https://itunes.apple.com/search";

    @Cacheable(value = "songs", key = "'itunes-' + #query")
    public List<SongResponse> searchExternalSongs(String query) {
        try {
            URI uri = UriComponentsBuilder.fromHttpUrl(ITUNES_API_URL)
                    .queryParam("term", query)
                    .queryParam("entity", "song")
                    .queryParam("limit", 25)
                    .build()
                    .encode()
                    .toUri();

            log.info("Fetching real music tracks from iTunes API: {}", uri);
            iTunesResponse response = restTemplate.getForObject(uri, iTunesResponse.class);

            if (response == null || response.getResults() == null) {
                return new ArrayList<>();
            }

            List<SongResponse> songList = new ArrayList<>();
            for (iTunesResponse.iTunesTrack track : response.getResults()) {
                SongResponse s = new SongResponse();
                s.setId(track.getTrackId());
                s.setTitle(track.getTrackName());
                s.setArtistName(track.getArtistName());
                s.setAlbumTitle(track.getCollectionName());
                s.setGenre(track.getPrimaryGenreName());
                s.setAudioUrl(track.getPreviewUrl());
                // Get higher resolution artwork by replacing 100x100 with 600x600
                if (track.getArtworkUrl100() != null) {
                    s.setAlbumCoverUrl(track.getArtworkUrl100().replace("100x100bb.jpg", "600x600bb.jpg"));
                }
                if (track.getTrackTimeMillis() != null) {
                    s.setDurationSeconds(track.getTrackTimeMillis() / 1000);
                }
                songList.add(s);
            }
            return songList;
        } catch (Exception e) {
            log.error("Failed to query iTunes API: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}
