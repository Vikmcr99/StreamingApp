package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


class UserTest {

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Should create user account on streaming")
    void createAccountOnStreaming() {
        Plan plan = new Plan();
        plan.setId(1L);
        plan.setName("Basic Plan");
        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("1234567890");
        card.setAvailable_limit(1000.0);

        user.createAccountOnStreaming("User Dummy", plan, card);

        assertEquals("User Dummy", user.getName());
        assertFalse(user.getSubscriptionList().isEmpty());
        assertFalse(user.getCards().isEmpty());
        assertEquals("Favorites", user.getPlaylists().get(0).getName());
        assertEquals("Basic Plan", user.getSubscriptionList().get(0).getPlan().getName());
    }

    @Test
    @DisplayName("Should create playlist")
    void createPlaylist() {
        user.createPlaylist("Favorites", false);
        assertEquals("Favorites", user.getPlaylists().get(0).getName());
    }

    @Test
    @DisplayName("Should create default playlist")
    void createDefaultPlaylist() {
        user.createPlaylist();
        assertEquals("Favorites", user.getPlaylists().get(0).getName());
    }


    @Test
    @DisplayName("Should subscribe to plan")
    void subscribeToPlan() {
        Plan plan = new Plan();
        plan.setId(1L);
        plan.setName("Basic Plan");
        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("1234567890");
        card.setAvailable_limit(1000.0);

        user.subscribeToPlan(plan, card);

        assertEquals(1, user.getSubscriptionList().size());
        assertTrue(user.getSubscriptionList().get(0).isActive());
        assertEquals(plan, user.getSubscriptionList().get(0).getPlan());
    }

    @Test
    @DisplayName("Should deactivate existing subscription when subscribing to new plan")
    void deactivateExistingSubscriptionWhenSubscribingToNewPlan() {
        Plan oldPlan = new Plan();
        oldPlan.setId(1L);
        oldPlan.setName("Old Plan");

        Plan newPlan = new Plan();
        newPlan.setId(2L);
        newPlan.setName("New Plan");

        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("1234567890");
        card.setAvailable_limit(1000.0);


        Subscription existingSubscription = new Subscription();
        existingSubscription.setActive(true);
        existingSubscription.setPlan(oldPlan);
        user.getSubscriptionList().add(existingSubscription);


        user.subscribeToPlan(newPlan, card);

        assertFalse(existingSubscription.isActive());


        assertEquals(2, user.getSubscriptionList().size());
        assertTrue(user.getSubscriptionList().get(1).isActive());
        assertEquals(newPlan, user.getSubscriptionList().get(1).getPlan());
    }


    @Test
    @DisplayName("Should test getters and setters")
    void testGettersAndSetters() {
        user.setId(1L);
        user.setName("Jon Travolta");
        user.setEmail("jon@example.com");
        user.setCpf("123456789");
        user.setPassword("password");

        List<Card> cards = new ArrayList<>();
        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("1234567890");
        card.setAvailable_limit(1000.0);
        cards.add(card);
        user.setCards(cards);

        List<Playlist> playlists = new ArrayList<>();
        Playlist playlist = new Playlist();
        playlist.setId(1L);
        playlist.setName("Favorites");
        playlist.setOpen(true);
        playlists.add(playlist);
        user.setPlaylists(playlists);

        List<Subscription> subscriptions = new ArrayList<>();
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscription.setActive(true);
        subscription.setDate(new Date());
        Plan plan = new Plan();
        plan.setId(1L);
        plan.setName("Basic Plan");
        subscription.setPlan(plan);
        subscriptions.add(subscription);
        user.setSubscriptionList(subscriptions);


        assertEquals(1L, user.getId());
        assertEquals("Jon Travolta", user.getName());
        assertEquals("jon@example.com", user.getEmail());
        assertEquals("123456789", user.getCpf());
        assertEquals("password", user.getPassword());
        assertEquals(cards, user.getCards());
        assertEquals(playlists, user.getPlaylists());
        assertEquals(subscriptions, user.getSubscriptionList());
    }


}
