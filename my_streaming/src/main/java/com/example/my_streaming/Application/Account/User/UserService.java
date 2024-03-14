package com.example.my_streaming.Application.Account.User;

import com.example.my_streaming.Application.Streaming.Band.BandRepository;
import com.example.my_streaming.Application.Streaming.Music.Music;
import com.example.my_streaming.Application.Transactions.Card.Card;
import com.example.my_streaming.Application.Transactions.Plan.Plan;
import com.example.my_streaming.Application.Transactions.Plan.PlanRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public User createUser(String name, Long planId, Card card) {
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

        return user;
    }

    public void favoriteMusic(Long userId, Long musicId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Music music = verifyMusic(musicId);
            user.favoriteMusic(music, "Favorites");

            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void unfavoriteMusic(Long userId, Long musicId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Music music = verifyMusic(musicId);
            user.unfavoriteMusic(music, "Favorites");

            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }


    private Music verifyMusic(Long musicId) {
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
