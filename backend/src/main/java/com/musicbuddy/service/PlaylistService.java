package com.musicbuddy.service;

import com.musicbuddy.dto.request.PlaylistRequest;
import com.musicbuddy.dto.response.PlaylistResponse;
import com.musicbuddy.entity.*;
import com.musicbuddy.exception.BadRequestException;
import com.musicbuddy.exception.ResourceNotFoundException;
import com.musicbuddy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final SongRepository songRepository;
    private final SongService songService;

    public List<PlaylistResponse> getUserPlaylists(Long userId) {
        return playlistRepository.findByUserId(userId).stream()
                .map(p -> toResponse(p, userId)).collect(Collectors.toList());
    }

    public PlaylistResponse getPlaylistById(Long id, Long userId) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", id));
        if (!playlist.isPublic() && !playlist.getUser().getId().equals(userId))
            throw new BadRequestException("Access denied to this playlist");
        return toResponse(playlist, userId);
    }

    @Transactional
    public PlaylistResponse createPlaylist(PlaylistRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Playlist playlist = Playlist.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isPublic(request.isPublic())
                .user(user)
                .build();
        return toResponse(playlistRepository.save(playlist), userId);
    }

    @Transactional
    public PlaylistResponse updatePlaylist(Long id, PlaylistRequest request, Long userId) {
        Playlist playlist = playlistRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", id));
        playlist.setName(request.getName());
        playlist.setDescription(request.getDescription());
        playlist.setPublic(request.isPublic());
        return toResponse(playlistRepository.save(playlist), userId);
    }

    @Transactional
    public void deletePlaylist(Long id, Long userId) {
        Playlist playlist = playlistRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", id));
        playlistRepository.delete(playlist);
    }

    @Transactional
    public PlaylistResponse addSong(Long playlistId, Long songId, Long userId) {
        Playlist playlist = playlistRepository.findByIdAndUserId(playlistId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", playlistId));
        Song song = songRepository.findById(songId)
                .orElseThrow(() -> new ResourceNotFoundException("Song", songId));
        if (!playlist.getSongs().contains(song)) playlist.getSongs().add(song);
        return toResponse(playlistRepository.save(playlist), userId);
    }

    @Transactional
    public PlaylistResponse removeSong(Long playlistId, Long songId, Long userId) {
        Playlist playlist = playlistRepository.findByIdAndUserId(playlistId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist", playlistId));
        playlist.getSongs().removeIf(s -> s.getId().equals(songId));
        return toResponse(playlistRepository.save(playlist), userId);
    }

    private PlaylistResponse toResponse(Playlist playlist, Long userId) {
        PlaylistResponse r = new PlaylistResponse();
        r.setId(playlist.getId());
        r.setName(playlist.getName());
        r.setDescription(playlist.getDescription());
        r.setPublic(playlist.isPublic());
        r.setOwnerUsername(playlist.getUser().getUsername());
        r.setCreatedAt(playlist.getCreatedAt());
        var songs = playlist.getSongs().stream()
                .map(s -> songService.toResponse(s, userId)).collect(Collectors.toList());
        r.setSongs(songs);
        r.setSongCount(songs.size());
        return r;
    }
}
