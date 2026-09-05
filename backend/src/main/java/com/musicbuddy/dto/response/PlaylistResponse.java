package com.musicbuddy.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PlaylistResponse {
    private Long id;
    private String name;
    private String description;
    private boolean isPublic;
    private String ownerUsername;
    private List<SongResponse> songs;
    private int songCount;
    private LocalDateTime createdAt;
}
