package com.example.my_streaming.Domain.Streaming.Band;

import com.example.my_streaming.Domain.Streaming.Album.Album;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_band")
public class Band {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    private String name;
    private String description;
   // private List<Album> albums;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Album> getAlbums() {
//        return albums;
//    }
//
//    public void setAlbums(List<Album> albums) {
//        this.albums = albums;
//    }
}
