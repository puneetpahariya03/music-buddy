package com.musicbuddy.service;

import com.musicbuddy.dto.response.LyricsResponse;
import com.musicbuddy.dto.response.SongResponse;
import com.musicbuddy.dto.response.iTunesResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicDiscoveryService {

    private final RestTemplate restTemplate;

    private static final String ITUNES_API_URL = "https://itunes.apple.com/search";

    @Cacheable(value = "songs", key = "'itunes-' + #query")
    public List<SongResponse> searchExternalSongs(String query) {
        try {
            String term = (query == null || query.isBlank()) ? "bollywood hits" : query.trim();

            URI uri = UriComponentsBuilder.fromHttpUrl(ITUNES_API_URL)
                    .queryParam("term", term)
                    .queryParam("entity", "song")
                    .queryParam("country", "IN")
                    .queryParam("limit", 30)
                    .build()
                    .encode()
                    .toUri();

            log.info("Querying live Bollywood / Indian music catalog from Apple: {}", uri);
            iTunesResponse response = restTemplate.getForObject(uri, iTunesResponse.class);

            if (response != null && response.getResults() != null) {
                return response.getResults().stream()
                        .map(this::mapTrackToSongResponse)
                        .toList();
            }
        } catch (Exception e) {
            log.warn("Apple Music API lookup failed: {}", e.getMessage());
        }
        return Collections.emptyList();
    }

    private static final Map<String, String> CURATED_LYRICS = new HashMap<>();

    static {
        CURATED_LYRICS.put("makhna",
                """
                Haan ji, kivein aan saare?
                Welcome to the wild side!

                Yeh bhi na jaane, woh bhi na jaane
                Naino ke rang naina jaane
                Mila jo sang tera, uda patang mera
                Hawa mein hoke malang!

                Do din ki zindagi hai
                Do din ka mela
                Khaali haath aayi thi
                Khaali haath jaana

                Sun ae hasina, kajra laga ke
                Khaali peeli akhiyan na maar
                Chadh gayi sharaab jaise
                Kudiye tere sar pe mera bukhaar!

                O mere makhna, makhna, o mere makhna
                O mere makhna, makhna, o mere makhna
                Makhna, makhna, makhna
                Makhna, makhna, makhna

                Teri hi baatein ho, tere hi charche
                Subah se shaam tak dil mera harfe
                Tu jo kahe toh main saari yeh duniya
                Chhod ke tere sang chalu hawa banke!

                Do din ki zindagi hai, do din ka mela
                Khaali haath aayi thi, khaali haath jaana
                Sun ae hasina, kajra laga ke
                Khaali peeli akhiyan na maar

                O mere makhna, makhna, o mere makhna
                O mere makhna, makhna, o mere makhna!
                """);

        CURATED_LYRICS.put("sonisoni",
                """
                Soni soni akhiyan, mithi mithi batiyan
                Tere bina kat-ti naahi saari ratiyan
                Ho dil mera khoya tere pyaar mein
                Lagta na mann sansaar mein

                Tu hi mera chain, tu hi meri pyaas
                Tu jo paas rahe, sab kuch hai khaas
                Soni soni akhiyan, mithi mithi batiyan
                Tere bina kat-ti naahi saari ratiyan!

                Aaja mahi aaja, gale se laga ja
                Ishq da rang tu mujhpe chadha ja
                Meri har saans pe naam tera
                Tu hi subah meri, tu hi mera savera!
                """);

        CURATED_LYRICS.put("chandigarhmein",
                """
                Dila de ghar, dila de ghar
                Dila de ghar, Chandigarh mein
                Dila de ghar, dila de ghar
                Dila de ghar, Chandigarh mein!

                Ho kudiyan de shehar vich
                Gabru da naam chale
                Tu jo kudiye naal mere
                Har koi taali maare!

                Aankhon se pilati hai
                Dil ko lubhati hai
                Chandigarh wali kudi
                Hosh udati hai!

                Dila de ghar, dila de ghar
                Dila de ghar, Chandigarh mein!
                """);

        CURATED_LYRICS.put("kesariya",
                """
                Mujhko itna bataaye koyi
                Kaise tujhse dil na lagaaye koyi?
                Rabba ne tujhko banaane mein
                Kardi hai husn ki khaali tijoriyaan

                Kaajal ki siyaahi se likhi
                Hai tune jaane kitnon ki love storiyaan!

                Kesariya tera ishq hai piya
                Rang jaaun jo main haath lagaun
                Din beete saara teri fikr mein
                Rain saari teri khair manaun!

                Patjhad ke mausam mein bhi
                Rangi chanar jaisi
                Jhanke sannaate mein tu
                Veena ke taar jaisi!

                Kesariya tera ishq hai piya
                Rang jaaun jo main haath lagaun!
                """);

        CURATED_LYRICS.put("chaleya",
                """
                Ishq mein dil bana hai
                Bada be-thikaana
                Tere peeche peeche
                Yeh chale deewana

                Jaane jaana, haan zamaana
                Dekhe saara pyaar tera
                Chaleya teri ore
                Chaleya teri ore
                Duniya bhula ke chalu
                Bas teri hi ore!

                Rang tera chadh gaya
                Rooh mein utar gaya
                Tere bina jeena ab
                Mushkil sa ho gaya!

                Chaleya teri ore
                Chaleya teri ore
                Ishq hua hai aisa
                Na koi zor!
                """);

        CURATED_LYRICS.put("tumhiho",
                """
                Hum tere bin ab reh nahi sakte
                Tere bina kya wajood mera
                Tujhse juda gar ho jaayenge
                Toh khud se hi ho jaayenge judaa

                Kyunki tum hi ho
                Ab tum hi ho
                Zindagi ab tum hi ho
                Chain bhi, mera dard bhi
                Meri aashiqui ab tum hi ho

                Tera mera rishta hai kaisa
                Ik pal door gawaara nahi
                Tere liye har roz hai jeete
                Tujhko diya mera waqt sabhi

                Kyunki tum hi ho
                Ab tum hi ho
                Zindagi ab tum hi ho!
                """);

        CURATED_LYRICS.put("satranga",
                """
                Aadha aadha sa tha main
                Pura tune kiya re
                Gham ka dariya tha dil
                Khushi se bhar diya re

                Satranga yeh ishq tera
                Rang saare khil gaye
                Bhatak rahe the andheron mein
                Manzil se mil gaye

                Aisi lagan lagi tere naam ki
                Duniya bhula di subah-o-shaam ki
                Satranga ishq mera
                Satranga ishq mera!
                """);

        CURATED_LYRICS.put("apnabanale",
                """
                Tu mera koi na
                Hoke bhi kuch laage
                Tu mera koi na
                Hoke bhi kuch laage

                Kiya re jo bhi tune
                Kaise kiya re
                Jiya ko mere baandh
                Aise liya re

                Apna bana le piya
                Apna bana le piya
                Dil ke nagar mein
                Shehar tu basa le piya

                Chhute na kabhi tera daaman
                Yaara mere yaara
                Apna bana le piya!
                """);

        CURATED_LYRICS.put("omaahi",
                """
                O Maahi, O Maahi
                Tere ishq mein dubi raahi
                Tere bina kya jeena mera
                Tu hi safar, tu hi panaah

                Dekha jo tujhko
                Ruk si gayi saansein
                Palkon pe tehra
                Chehra tera raatein

                O Maahi ve, O Maahi ve
                Tu hai jahaan, main wahaan
                Tere bin adhura jahaan!
                """);

        CURATED_LYRICS.put("raataanlambiyan",
                """
                Teri meri gallan ho gayi mashhur
                Kar na kabhi tu mujhe nazron se door
                Kithe chaliye tu kithe chaliye
                Jaanda ae dil yeh toh jaandi ae tu

                Raataan lambiyan lambiyan re
                Kate tere sangeyaan sangeyaan re
                Kaise yeh beeti hain main hi jaanoon
                Naina yeh baatein hain main hi maanoon!
                """);

        CURATED_LYRICS.put("ilahi",
                """
                Shaamein malang si
                Raatein surmayi
                Khwabon ke jaahan mein
                Manzil nayi

                Kal pe sawaal hai
                Jeena filhaal hai
                Khaanabadosh dil
                Mera behaal hai

                Ilahi mera jee aaye aaye
                Ilahi mera jee aaye aaye!
                """);

        CURATED_LYRICS.put("bekhayali",
                """
                Bekhayali mein bhi tera
                Hi khayaal aaye
                Kyun bichhadna hai zaroori
                Ye sawaal aaye

                Har lamha aashiqui ka
                Gham de raha hai
                Tujhse juda hoke dil
                Ro raha hai

                Bekhayali mein bhi tera
                Hi khayaal aaye!
                """);
    }

    @Cacheable(value = "songs", key = "'lyrics-' + #artist + '-' + #title")
    public String getLyrics(String artist, String title) {
        String cleanArtist = artist.split("[,&|/]|\\bfeat\\b|\\bft\\b")[0].trim();
        String cleanTitle = title.replaceAll("(?i)\\s*(\\(.*?\\)|\\[.*?\\]|feat\\..*|ft\\..*|-.*)", "").trim();
        String key = cleanTitle.toLowerCase().replaceAll("[^a-z0-9]", "");

        // 1. Check verified curated store
        if (CURATED_LYRICS.containsKey(key)) {
            return CURATED_LYRICS.get(key).trim();
        }

        // 2. Query LRCLIB with identifying User-Agent
        try {
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("User-Agent", "MusicBuddy/1.0 (https://github.com/puneetpahariya03/music-buddy)");
            org.springframework.http.HttpEntity<Void> entity = new org.springframework.http.HttpEntity<>(headers);

            String url = UriComponentsBuilder.fromHttpUrl("https://lrclib.net/api/get")
                    .queryParam("track_name", cleanTitle)
                    .queryParam("artist_name", cleanArtist)
                    .build().encode().toUriString();

            org.springframework.http.ResponseEntity<com.musicbuddy.dto.response.LrcLibResponse> resp =
                    restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, entity, com.musicbuddy.dto.response.LrcLibResponse.class);

            if (resp.getBody() != null && resp.getBody().getPlainLyrics() != null && !resp.getBody().getPlainLyrics().isBlank()) {
                return resp.getBody().getPlainLyrics().trim();
            }
        } catch (Exception e) {
            log.debug("LRCLIB direct lookup failed for {} - {}", cleanArtist, cleanTitle);
        }

        // 3. Fallback to Lyrics.ovh
        try {
            String lyricsUrl = "https://api.lyrics.ovh/v1/" + cleanArtist + "/" + cleanTitle;
            LyricsResponse res = restTemplate.getForObject(lyricsUrl, LyricsResponse.class);
            if (res != null && res.getLyrics() != null && !res.getLyrics().isBlank()) {
                return res.getLyrics().trim();
            }
        } catch (Exception e) {
            log.debug("Lyrics.ovh lookup failed for {} - {}", cleanArtist, cleanTitle);
        }

        return "Lyrics for \"" + title + "\" by " + artist + " are currently being curated.\n\nEnjoy the melody and rhythm!";
    }

    private SongResponse mapTrackToSongResponse(iTunesResponse.iTunesTrack track) {
        SongResponse s = new SongResponse();
        s.setId(track.getTrackId());
        s.setTitle(track.getTrackName());
        s.setArtistName(track.getArtistName());
        s.setAlbumTitle(track.getCollectionName());
        s.setGenre(track.getPrimaryGenreName());
        s.setAudioUrl(track.getPreviewUrl());
        if (track.getArtworkUrl100() != null) {
            s.setAlbumCoverUrl(track.getArtworkUrl100().replace("100x100bb.jpg", "600x600bb.jpg"));
        }
        if (track.getTrackTimeMillis() != null) {
            s.setDurationSeconds(track.getTrackTimeMillis() / 1000);
        }
        return s;
    }
}
