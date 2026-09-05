package com.musicbuddy.service;

import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.entity.*;
import com.musicbuddy.exception.ResourceNotFoundException;
import com.musicbuddy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final SongService songService;

    public List<SongResponse> getFavorites(Long userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(f -> songService.toResponse(f.getSong(), userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean toggleFavorite(Long songId, Long userId) {
        if (favoriteRepository.existsByUserIdAndSongId(userId, songId)) {
            favoriteRepository.deleteByUserIdAndSongId(userId, songId);
            return false;
        }
        User user = userRepository.findById(userId).orElseThrow();
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song", songId));
        favoriteRepository.save(Favorite.builder().user(user).song(song).build());
        return true;
    }
}
