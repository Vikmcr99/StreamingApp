package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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


}
