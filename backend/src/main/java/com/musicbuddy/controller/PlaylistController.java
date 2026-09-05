package com.musicbuddy.controller;

import com.musicbuddy.dto.request.PlaylistRequest;
import com.musicbuddy.dto.response.ApiResponse;
import com.musicbuddy.dto.response.PlaylistResponse;
import com.musicbuddy.entity.User;
import com.musicbuddy.repository.UserRepository;
import com.musicbuddy.service.PlaylistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserRepository userRepository;

    private Long userId(UserDetails ud) {
        return userRepository.findByUsername(ud.getUsername()).map(User::getId).orElseThrow();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PlaylistResponse>>> getMine(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(playlistService.getUserPlaylists(userId(ud))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> getById(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(playlistService.getPlaylistById(id, userId(ud))));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PlaylistResponse>> create(
            @Valid @RequestBody PlaylistRequest request, @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success("Playlist created", playlistService.createPlaylist(request, userId(ud))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> update(
            @PathVariable Long id, @Valid @RequestBody PlaylistRequest request,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success("Playlist updated", playlistService.updatePlaylist(id, request, userId(ud))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable Long id, @AuthenticationPrincipal UserDetails ud) {
        playlistService.deletePlaylist(id, userId(ud));
        return ResponseEntity.ok(ApiResponse.success("Playlist deleted", null));
    }

    @PostMapping("/{id}/songs/{songId}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> addSong(
            @PathVariable Long id, @PathVariable Long songId,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(playlistService.addSong(id, songId, userId(ud))));
    }

    @DeleteMapping("/{id}/songs/{songId}")
    public ResponseEntity<ApiResponse<PlaylistResponse>> removeSong(
            @PathVariable Long id, @PathVariable Long songId,
            @AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(playlistService.removeSong(id, songId, userId(ud))));
    }
}
