package com.musicbuddy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SongRequest {
    @NotBlank private String title;
    private Integer durationSeconds;
    private String genre;
    private String audioUrl;
    @NotNull private Long artistId;
    private Long albumId;
}
