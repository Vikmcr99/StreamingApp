package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Requests.CardRequest;
import com.example.my_streaming.Requests.CreateUserRequest;
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

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

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
    void testFindById_UserNotExists() {

        when(userService.getById(anyLong())).thenThrow(NoSuchElementException.class);

        ResponseEntity<UserResponse> responseEntity = userController.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
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