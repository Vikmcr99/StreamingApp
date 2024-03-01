package com.example.my_streaming.Domain.Account.Playlist;

import com.example.my_streaming.Domain.Account.User.User;
import com.example.my_streaming.Domain.Streaming.Music.Music;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_playlist")
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String name;
    private boolean open;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany (mappedBy = "playlist", cascade = CascadeType.ALL)
    private List<Music> musics;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }
}
