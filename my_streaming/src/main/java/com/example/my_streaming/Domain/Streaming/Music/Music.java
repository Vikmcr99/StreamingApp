package com.example.my_streaming.Domain.Streaming.Music;

import com.example.my_streaming.Domain.Account.Playlist.Playlist;
import com.example.my_streaming.Domain.Streaming.Album.Album;
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
    //private Album album;


    public Music(Long id, String name, double duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
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

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "playlist_id")
    private Playlist playlist;

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

//    public Album getAlbum() {
//        return album;
//    }
//
//    public void setAlbum(Album album) {
//        this.album = album;
//    }
}

