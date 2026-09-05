package com.musicbuddy.controller;

import com.musicbuddy.dto.request.ArtistRequest;
import com.musicbuddy.dto.response.ApiResponse;
import com.musicbuddy.dto.response.ArtistResponse;
import com.musicbuddy.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ArtistResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success(artistService.getAllArtists()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArtistResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(artistService.getArtistById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ArtistResponse>> create(@Valid @RequestBody ArtistRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Artist created", artistService.createArtist(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ArtistResponse>> update(
            @PathVariable Long id, @Valid @RequestBody ArtistRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Artist updated", artistService.updateArtist(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok(ApiResponse.success("Artist deleted", null));
    }
}
