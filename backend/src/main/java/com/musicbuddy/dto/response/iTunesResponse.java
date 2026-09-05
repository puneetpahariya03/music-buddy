package com.musicbuddy.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class iTunesResponse {
    private int resultCount;
    private List<iTunesTrack> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class iTunesTrack {
        private Long trackId;
        private String trackName;
        private String artistName;
        private String collectionName;
        private String previewUrl;
        private String artworkUrl100;
        private String primaryGenreName;
        private Integer trackTimeMillis;
    }
}
