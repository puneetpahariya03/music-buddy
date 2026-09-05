package com.musicbuddy.controller;

import com.musicbuddy.dto.response.ApiResponse;
import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.repository.UserRepository;
import com.musicbuddy.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepository;

    private Long userId(UserDetails ud) {
        return userRepository.findByUsername(ud.getUsername()).map(u -> u.getId()).orElseThrow();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SongResponse>>> getFavorites(@AuthenticationPrincipal UserDetails ud) {
        return ResponseEntity.ok(ApiResponse.success(favoriteService.getFavorites(userId(ud))));
    }

    @PostMapping("/{songId}")
    public ResponseEntity<ApiResponse<Map<String, Boolean>>> toggle(
            @PathVariable Long songId, @AuthenticationPrincipal UserDetails ud) {
        boolean added = favoriteService.toggleFavorite(songId, userId(ud));
        return ResponseEntity.ok(ApiResponse.success(Map.of("favorited", added)));
    }
}
