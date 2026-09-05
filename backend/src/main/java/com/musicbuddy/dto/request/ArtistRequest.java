package com.musicbuddy.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ArtistRequest {
    @NotBlank private String name;
    private String bio;
    private String imageUrl;
}
