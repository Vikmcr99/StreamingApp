package com.example.my_streaming.Application.Streaming.Music;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_music")
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @Column
    private String name;
    @Column
    private double duration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;


    public Music(Long id, String name, double duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public Music(){

    }

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


    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }


}

