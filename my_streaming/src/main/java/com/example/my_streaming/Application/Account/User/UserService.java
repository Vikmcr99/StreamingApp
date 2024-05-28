package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Account.Notification.Notify;
import com.example.my_streaming.Application.Account.Notification.NotifyAzureServiceBus;
import com.example.my_streaming.Application.Account.Playlist.Playlist;
import com.example.my_streaming.Application.Streaming.Band.BandRepository;
import com.example.my_streaming.Application.Streaming.Music.Music;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Plan.PlanRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private BandRepository bandRepository;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public User createUser(String name, Long planId, Card card) throws ExecutionException, InterruptedException, JsonProcessingException {
        Plan plan = planRepository.getPlanById(planId);
        if (plan == null) {
            throw new RuntimeException("Plan not found");
        }

        User user = new User();
        user.createAccountOnStreaming(name, plan, card);

        card.setUser(user);
        user.getCards().add(card);


         if (!entityManager.contains(user)) {
            user = entityManager.merge(user);
        }


        userRepository.save(user);

        Notify notify = new Notify();
        notify.setId_user(user.getId());
        notify.setMessage("basic plan successfully activated for a value of " + plan.getPlan_value());

        NotifyAzureServiceBus serviceBus = new NotifyAzureServiceBus();
        serviceBus.sendMessage(notify);


        return user;
    }

    public void favoriteMusic(Long userId, Long musicId, String playlistName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Music music = verifyMusic(musicId);

        Playlist favoritesPlaylist = getPlaylistByName(user, playlistName);

        if (!favoritesPlaylist.getMusics().contains(music)) {
            favoritesPlaylist.getMusics().add(music);
        }

        userRepository.save(user);
    }

    public void unfavoriteMusic(Long userId, Long musicId, String playlistName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Music music = verifyMusic(musicId);

        Playlist favoritesPlaylist = getPlaylistByName(user, playlistName);
        favoritesPlaylist.getMusics().removeIf(m -> m.getId().equals(music.getId()));
        userRepository.save(user);

    }

    private Playlist getPlaylistByName(User user, String playlistName) {
       return user.getPlaylists().stream()
                .filter(playlist -> playlist.getName().equals(playlistName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Playlist not found" + playlistName));
    }


    public Music verifyMusic(Long musicId) {
        Music music = bandRepository.getMusic(musicId);
        if (music == null) {
            throw new RuntimeException("did not find the requested music");
        }
        return music;
    }


    @Transactional(readOnly = true)
    public User getById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
