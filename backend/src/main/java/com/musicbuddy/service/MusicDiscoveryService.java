package com.musicbuddy.service;

import com.musicbuddy.dto.response.LyricsResponse;
import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.dto.response.iTunesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
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
            String term = (query == null || query.isBlank()) ? "bollywood hits" : query.trim();

            URI uri = UriComponentsBuilder.fromHttpUrl(ITUNES_API_URL)
                    .queryParam("term", term)
                    .queryParam("entity", "song")
                    .queryParam("country", "IN")
                    .queryParam("limit", 30)
                    .build()
                    .encode()
                    .toUri();

            log.info("Querying live Bollywood / Indian music catalog from Apple: {}", uri);
            iTunesResponse response = restTemplate.getForObject(uri, iTunesResponse.class);

            if (response != null && response.getResults() != null) {
                return response.getResults().stream()
                        .map(this::mapTrackToSongResponse)
                        .toList();
            }
        } catch (Exception e) {
            log.warn("Apple Music API lookup failed: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    @Cacheable(value = "songs", key = "'lyrics-' + #artist + '-' + #title")
    public String getLyrics(String artist, String title) {
        try {
            // Clean artist and title for higher lyrics match accuracy
            String cleanArtist = artist.split("[,&|/]")[0].trim();
            String cleanTitle = title.replaceAll("(?i)\\s*(\\(.*?\\)|\\[.*?\\]|feat\\..*|ft\\..*|-.*)", "").trim();

            String lyricsUrl = "https://api.lyrics.ovh/v1/" + cleanArtist + "/" + cleanTitle;
            log.info("Fetching lyrics from: {}", lyricsUrl);

            LyricsResponse res = restTemplate.getForObject(lyricsUrl, LyricsResponse.class);
            if (res != null && res.getLyrics() != null && !res.getLyrics().isBlank()) {
                return res.getLyrics();
            }
        } catch (Exception e) {
            log.debug("Lyrics not found from provider for {} - {}", artist, title);
        }

        return "Lyrics for \"" + title + "\" by " + artist + " are currently being curated.\n\nEnjoy the melody and rhythm!";
    }

    private SongResponse mapTrackToSongResponse(iTunesResponse.iTunesTrack track) {
        SongResponse s = new SongResponse();
        s.setId(track.getTrackId());
        s.setTitle(track.getTrackName());
        s.setArtistName(track.getArtistName());
        s.setAlbumTitle(track.getCollectionName());
        s.setGenre(track.getPrimaryGenreName());
        s.setAudioUrl(track.getPreviewUrl());
        if (track.getArtworkUrl100() != null) {
            s.setAlbumCoverUrl(track.getArtworkUrl100().replace("100x100bb.jpg", "600x600bb.jpg"));
        }
        if (track.getTrackTimeMillis() != null) {
            s.setDurationSeconds(track.getTrackTimeMillis() / 1000);
        }
        return s;
    }
}
