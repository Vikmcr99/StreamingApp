package com.example.my_streaming.Responses;

import java.util.ArrayList;
import java.util.List;

public class PlaylistResponse {
    private Long id;
    private String name;
    private List<MusicResponse> musics = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MusicResponse> getMusics() {
        return musics;
    }

    public void setMusics(List<MusicResponse> music) {
        this.musics = musics;
    }
}
