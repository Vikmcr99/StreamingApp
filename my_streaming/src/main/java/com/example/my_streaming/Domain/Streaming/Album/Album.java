package com.example.my_streaming.Domain.Streaming.Album;

import com.example.my_streaming.Domain.Streaming.Band.Band;
import com.example.my_streaming.Domain.Streaming.Music.Music;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_album")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;
    //private Band band;

    //private List<Music> musics;

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

//    public Band getBand() {
//        return band;
//    }
//
//    public void setBand(Band band) {
//        this.band = band;
//    }

   /* public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }*/
}
