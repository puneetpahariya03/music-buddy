package com.musicbuddy.controller;

import com.musicbuddy.dto.request.SongRequest;
import com.musicbuddy.dto.response.ApiResponse;
import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.entity.User;
import com.musicbuddy.repository.UserRepository;
import com.musicbuddy.service.MusicDiscoveryService;
import com.musicbuddy.service.SongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private final MusicDiscoveryService musicDiscoveryService;
    private final UserRepository userRepository;

    private Long currentUserId(UserDetails ud) {
        if (ud == null) return null;
        return userRepository.findByUsername(ud.getUsername()).map(User::getId).orElse(null);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<SongResponse>>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(songService.getAllSongs(page, size, currentUserId(ud))));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<SongResponse>>> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(songService.searchSongs(q, page, size, currentUserId(ud))));
    }

    @GetMapping("/explore")
    public ResponseEntity<ApiResponse<List<SongResponse>>> exploreiTunes(
            @RequestParam(defaultValue = "bollywood hits") String query) {
        return ResponseEntity.ok(ApiResponse.success(musicDiscoveryService.searchExternalSongs(query)));
    }

    @GetMapping("/lyrics")
    public ResponseEntity<ApiResponse<String>> getLyrics(
            @RequestParam String artist,
            @RequestParam String title) {
        return ResponseEntity.ok(ApiResponse.success("Lyrics retrieved", musicDiscoveryService.getLyrics(artist, title)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SongResponse>> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(songService.getSongById(id, currentUserId(ud))));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SongResponse>> create(@Valid @RequestBody SongRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Song created", songService.createSong(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SongResponse>> update(
            @PathVariable Long id, @Valid @RequestBody SongRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Song updated", songService.updateSong(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        songService.deleteSong(id);
        return ResponseEntity.ok(ApiResponse.success("Song deleted", null));
    }
}
