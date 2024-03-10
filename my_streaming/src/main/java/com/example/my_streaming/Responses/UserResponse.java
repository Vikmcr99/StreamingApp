package com.example.my_streaming.Responses;

import java.util.ArrayList;
import java.util.List;

public class UserResponse {
    private Long id;
    private String name;
    private Long planId;
    private List<PlaylistResponse> playlists = new ArrayList<>();

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


    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public List<PlaylistResponse> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistResponse> playlists) {
        this.playlists = playlists;
    }
}

