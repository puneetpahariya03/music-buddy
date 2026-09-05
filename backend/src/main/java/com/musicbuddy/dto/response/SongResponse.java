package com.musicbuddy.dto.response;

import lombok.Data;

@Data
public class SongResponse {
    private Long id;
    private String title;
    private Integer durationSeconds;
    private String genre;
    private String audioUrl;
    private String artistName;
    private Long artistId;
    private String albumTitle;
    private Long albumId;
    private String albumCoverUrl;
    private boolean favorited;
}
