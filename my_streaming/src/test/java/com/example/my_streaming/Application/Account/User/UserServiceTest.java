package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Streaming.Band.BandRepository;
import com.example.my_streaming.Application.Streaming.Music.Music;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Plan.PlanRepository;
import com.example.my_streaming.Responses.MusicResponse;
import com.example.my_streaming.Responses.PlaylistResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private BandRepository bandRepository;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DisplayName("Should create user successfuly")
    void createUserCase1() throws ExecutionException, InterruptedException, JsonProcessingException {

        when(planRepository.getPlanById(any())).thenReturn(new Plan(1L, "Basic Plan", "basic plan",29.99));

        when(entityManager.contains(any())).thenReturn(true);

        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("564654654");
        card.setAvailable_limit(1000.0);

        User createdUser = userService.createUser("User Dummy", 1L, card);

        assertNotNull(createdUser);
        assertTrue(!createdUser.getSubscriptionList().isEmpty());
        assertTrue(!createdUser.getCards().isEmpty());
        assertEquals("Favorites", createdUser.getPlaylists().get(0).getName());
        assertEquals(1, createdUser.getSubscriptionList().size());
        assertEquals("Basic Plan", createdUser.getSubscriptionList().get(0).getPlan().getName());

        verify(userRepository, times(1)).save(any());
    }


    @Test
    @DisplayName("Should throw an Exception when the user can not be created")
    void createUserCase2() {
        when(planRepository.getPlanById(any())).thenReturn(null);

        Card card = new Card();
        card.setActive_card(true);
        card.setCard_number("564654654");
        card.setAvailable_limit(1000.0);

        assertThrows(RuntimeException.class, () -> {
            userService.createUser("User Dummy", 1L, card);
        });
    }

    @Test
    @DisplayName("Should add music to user's favorite playlist")
    void favoriteMusic() {
        User user = new User();
        user.setId(1L);
        Playlist playlist = new Playlist();
        playlist.setName("Favorites");
        user.getPlaylists().add(playlist);

        Music music = new Music();
        music.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bandRepository.getMusic(1L)).thenReturn(music);

        userService.favoriteMusic(1L, 1L, "Favorites");

        assertTrue(playlist.getMusics().contains(music));
        verify(userRepository, times(1)).save(any(User.class));

        PlaylistResponse playlistResponse = new PlaylistResponse();
        List<MusicResponse> musicResponses = new ArrayList<>();
        for (Music m : playlist.getMusics()) {
            MusicResponse musicResponse = new MusicResponse();
            musicResponse.setId(m.getId());
            musicResponse.setName(m.getName());
            musicResponse.setDuration(m.getDuration());
            musicResponses.add(musicResponse);
        }
        playlistResponse.setMusics(musicResponses);
        assertNotNull(playlistResponse.getMusics());

    }

    @Test
    @DisplayName("Should remove music from user's favorite playlist")
    void unfavoriteMusic() {
        User user = new User();
        user.setId(1L);
        Playlist playlist = new Playlist();
        playlist.setName("Favorites");
        Music music = new Music();
        music.setId(1L);
        playlist.getMusics().add(music);
        user.getPlaylists().add(playlist);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(bandRepository.getMusic(1L)).thenReturn(music);

        userService.unfavoriteMusic(1L, 1L, "Favorites");

        assertFalse(playlist.getMusics().contains(music));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should return user by ID")
    void getById() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    @DisplayName("Should return all users")
    void getAllUsers() {
        List<User> users = List.of(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("Should delete user by ID")
    void deleteUser() {
        doNothing().when(userRepository).deleteById(1L);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw exception when verifying non-existing music")
    void throwExceptionWhenVerifyingNonExistingMusic() {

        when(bandRepository.getMusic(anyLong())).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
            userService.verifyMusic(1L);
        });
    }




}