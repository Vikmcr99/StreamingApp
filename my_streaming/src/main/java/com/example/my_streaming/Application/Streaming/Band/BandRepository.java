package com.example.my_streaming.Application.Streaming.Band;

import com.example.my_streaming.Application.Streaming.Music.Music;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class BandRepository {
    private final RestTemplate restTemplate;

    public BandRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Music getMusic(Long id) {
        String url = "http://localhost:9090/api/musics/" + id;

        Music music = restTemplate.getForObject(url, Music.class);

        if (music == null) {
            throw new RuntimeException("Cant search music");
        }

        return music;
    }
}