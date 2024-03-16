package com.example.my_streaming.Application.Streaming.Band;

import com.example.my_streaming.Application.Streaming.Music.Music;
import io.github.resilience4j.retry.Retry;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class BandRepository {
    private final RestTemplate restTemplate;
    private final Retry retry;

    public BandRepository(RestTemplate restTemplate, Retry retry) {

        this.restTemplate = restTemplate;
        this.retry = retry;
    }

    public Music getMusic(Long id) {
        String url = "http://localhost:9090/api/musics/" + id;

        return Retry.decorateSupplier(retry, () -> {
            Music music = restTemplate.getForObject(url, Music.class);
            if (music == null) {
                throw new RuntimeException("Cant search music");
            }
            return music;
        }).get();
    }
}