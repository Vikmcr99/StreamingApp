package com.example.my_streaming.Domain.Account.User;

import com.example.my_streaming.Domain.Transactions.Card.Card;
import com.example.my_streaming.Domain.Account.Playlist.Playlist;
import com.example.my_streaming.Domain.Transactions.Subscription.Subscription;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "tb_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String name;

    private String email;

    private String cpf;

    private String password;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Card> cards;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlist> playlists;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private List<Subscription> subscriptionList;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public List<Subscription> getSubscriptionList() {
        return subscriptionList;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
    }
}
