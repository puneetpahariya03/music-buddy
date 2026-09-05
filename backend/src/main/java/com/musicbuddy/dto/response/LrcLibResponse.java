package com.musicbuddy.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LrcLibResponse {
    private String plainLyrics;
    private String syncedLyrics;
}
