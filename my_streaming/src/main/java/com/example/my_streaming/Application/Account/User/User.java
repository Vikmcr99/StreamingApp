package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

    private Random random;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Card> cards = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Playlist> playlists = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER ,  cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Subscription> subscriptionList = new ArrayList<>();

    public User() {
        this.cards = new ArrayList<>();
        this.playlists = new ArrayList<>();
        this.subscriptionList = new ArrayList<>();
        this.random = new Random();
    }

    public Long generateNextLong(){
        return this.random.nextLong();
    }

    public void createAccountOnStreaming(String name, Plan plan, Card card) {
        this.name = name;
        this.subscribeToPlan(plan, card);
        this.addCard(card);
        this.createPlaylist();



    }

    public void createPlaylist(String name, boolean isOpen) {
        Playlist playlist = new Playlist();
        playlist.setId(generateNextLong());
        playlist.setName(name);
        playlist.setOpen(isOpen);
        playlist.setUser(this);
        this.playlists.add(playlist);
    }

    public void createPlaylist() {
        this.createPlaylist("Favorites", false);
    }

    private void addCard(Card card) {
        this.cards.add(card);
    }

    public void subscribeToPlan(Plan plan, Card card) {
        card.createTransaction(plan.getName(), plan.getPlan_value(), plan.getDescription());

        for (Subscription subscription : this.subscriptionList) {
            if (Boolean.TRUE.equals(subscription.isActive())) {
                subscription.setActive(false);
            }
        }

        Subscription newSubscription = new Subscription();
        newSubscription.setActive(true);
        newSubscription.setDate(new Date());
        newSubscription.setPlan(plan);
        newSubscription.setId(generateNextLong());
        newSubscription.setUser(this);
        this.subscriptionList.add(newSubscription);
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
