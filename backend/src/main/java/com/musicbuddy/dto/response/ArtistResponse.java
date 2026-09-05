package com.musicbuddy.dto.response;

import lombok.Data;

@Data
public class ArtistResponse {
    private Long id;
    private String name;
    private String bio;
    private String imageUrl;
    private int songCount;
}
