package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Streaming.Music.Music;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Subscription.Subscription;
import com.example.my_streaming.Requests.CreateUserRequest;
import com.example.my_streaming.Responses.MusicResponse;
import com.example.my_streaming.Responses.UserResponse;
import com.example.my_streaming.Responses.PlaylistResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/v1", produces = {"application/json"})
@Tag(name = "My_Streaming - UserController")

public class UserController {

    @Autowired
    private UserService service;

    //todo: Adicionado para os testes, testar se o codigo continua funcional
    public UserController(UserService userService) {
        this.service = userService;
    }

    @GetMapping(value="/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users =  service.getAllUsers();

        if (users == null || users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable("id") Long id) {
        try {
            User user = service.getById(id);
            UserResponse response = userToResponse(user);

            return new ResponseEntity<UserResponse>(response, HttpStatus.OK);
        }

        catch (NoSuchElementException ns) {
            return new ResponseEntity<UserResponse>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<UserResponse> createAccount(@RequestBody CreateUserRequest request) {
        try {
            Card card = new Card();
            card.setAvailable_limit(request.getCard().getLimit());
            card.setActive_card(request.getCard().getActive());
            card.setCard_number(request.getCard().getNumber());

            User user = service.createUser(request.getName(), request.getPlanId(), card);
            UserResponse response = userToResponse(user);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException | ExecutionException | InterruptedException | JsonProcessingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/users/{id}/favorite/{idMusic}")
    public ResponseEntity<UserResponse> FavoriteMusic(@PathVariable("id") Long id,
                                                      @PathVariable("idMusic") Long idMusic,
                                                      @RequestParam(value = "playlistName", defaultValue = "Favorites") String playlistName){

        try{

            User user = service.getById(id);
            service.favoriteMusic(id, idMusic, playlistName);


            UserResponse response = userToResponse(user);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/users/{id}/unfavorite/{idMusic}")
    public ResponseEntity<UserResponse> UnfavoriteMusic(@PathVariable("id") Long id,
                                                        @PathVariable("idMusic") Long idMusic,
                                                        @RequestParam(value = "playlistName", defaultValue = "Favorites") String playlistName){

        try{
            service.unfavoriteMusic(id, idMusic, playlistName);

            User user = service.getById(id);

            UserResponse response = userToResponse(user);

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @SuppressWarnings("rawtypes")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity deleteAccount(@PathVariable ("id") Long id) {
        try
        {
            service.deleteUser(id);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    private UserResponse userToResponse(User createdUser) {
        UserResponse response = new UserResponse();
        response.setId(createdUser.getId());
        response.setName(createdUser.getName());

        Subscription activeSubscription = createdUser.getSubscriptionList().stream()
                .filter(Subscription::isActive)
                .findFirst()
                .orElse(null);

        if (activeSubscription != null) {
            response.setPlanId(activeSubscription.getPlan().getId());
        } else {
            throw new RuntimeException("No active subscription found for the user: " + createdUser.getId());
        }

        for (Playlist playlist : createdUser.getPlaylists()) {
            PlaylistResponse playlistResponse = new PlaylistResponse();
            playlistResponse.setId(playlist.getId());
            playlistResponse.setName(playlist.getName());
            response.getPlaylists().add(playlistResponse);

            for (Music music : playlist.getMusics()) {
                MusicResponse musicResponse = new MusicResponse();
                musicResponse.setDuration(music.getDuration());
                musicResponse.setName(music.getName());
                musicResponse.setId(music.getId());
                playlistResponse.getMusics().add(musicResponse);
            }
        }

        return response;
    }
}

