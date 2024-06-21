package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Streaming.Band.BandRepository;
import com.example.my_streaming.Application.Streaming.Music.Music;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import com.example.my_streaming.Requests.CardRequest;
import com.example.my_streaming.Requests.CreateUserRequest;
import com.example.my_streaming.Responses.MusicResponse;
import com.example.my_streaming.Responses.PlaylistResponse;
import com.example.my_streaming.Responses.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private BandRepository bandRepository;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create user account")
    void createAccount() throws Exception {

        User user = new User();
        user.setName("User Dummy");
        user.setId(1L);

        when(userService.createUser(any(String.class), any(Long.class), any(Card.class)))
                .thenReturn(user);

        CreateUserRequest request = new CreateUserRequest();
        request.setName("User Dummy");
        request.setPlanId(1L);
        CardRequest card = new CardRequest();
        card.setLimit(1000.0);
        card.setActive(true);
        card.setNumber("1234567890");
        request.setCard(card);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("User Dummy"));
    }

    @Test
    @DisplayName("Should return bad request when creating user account fails")
    void createAccountBadRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setName("User Dummy");
        request.setPlanId(1L);

        CardRequest cardRequest = new CardRequest();
        cardRequest.setLimit(10.0);
        cardRequest.setActive(true);
        cardRequest.setNumber("1234567890");
        request.setCard(cardRequest);

        when(userService.createUser(any(), any(), any())).thenThrow(new RuntimeException());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("Should return no content when there are no users")
    void getAllUsersNoContent() throws Exception {
        when(userService.getAllUsers()).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }



    @Test
    void testDeleteAccount_Success() {
        UserService userService = mock(UserService.class);
        doNothing().when(userService).deleteUser(anyLong());

        UserController userController = new UserController(userService);

        ResponseEntity responseEntity = userController.deleteAccount(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteAccount_Failure() {
        UserService userService = mock(UserService.class);
        doThrow(new RuntimeException()).when(userService).deleteUser(anyLong());
        UserController userController = new UserController(userService);

        ResponseEntity responseEntity = userController.deleteAccount(1L);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testUserToResponse_NoActiveSubscription() {
        User user = new User();
        user.setId(1L);
        user.setName("Jonatan");

        UserService userService = mock(UserService.class);
        when(userService.getById(1L)).thenReturn(user);


        UserController userController = new UserController(userService);

        assertThrows(RuntimeException.class, () -> userController.findById(1L));
    }

    @Test
    @DisplayName("Should return BAD_REQUEST when adding favorite music fails")
    void returnBadRequestWhenAddingFavoriteMusicFails() {

        when(userService.getById(anyLong())).thenThrow(new RuntimeException());

        ResponseEntity<UserResponse> responseEntity = userController.FavoriteMusic(1L, 1L, "Favorites");
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }




@Test
    @DisplayName("Should return all users")
    void getAllUsers() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setName("User Dummy");

        when(userService.getAllUsers()).thenReturn(List.of(user));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}