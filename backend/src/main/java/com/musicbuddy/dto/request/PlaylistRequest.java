package com.musicbuddy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlaylistRequest {
    @NotBlank private String name;
    private String description;
    private boolean isPublic;
}
