package com.example.my_streaming.Domain.Account.User;

import com.example.my_streaming.Domain.Streaming.Band.BandRepository;
import com.example.my_streaming.Domain.Streaming.Music.Music;
import com.example.my_streaming.Domain.Transactions.Card.Card;
import com.example.my_streaming.Domain.Transactions.Plan.Plan;
import com.example.my_streaming.Domain.Transactions.Plan.PlanRepository;
import com.example.my_streaming.Requests.CardRequest;
import com.example.my_streaming.Requests.CreateUserRequest;
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

    public User createUser(CreateUserRequest createUserRequest) {
        Plan plan = planRepository.getPlanById(createUserRequest.getPlanId());
        if (plan == null) {
            throw new RuntimeException("Plan not found");
        }

        Card card = convertToCard(createUserRequest.getCard());

        User user = new User();
        user.createAccountOnStreaming(createUserRequest.getName(), plan, card);
        userRepository.save(user);

        return user;
    }

    private Card convertToCard(CardRequest cardRequest) {
        Card card = new Card();
        card.setCard_number(cardRequest.getNumber());
        card.setActive_card(cardRequest.getActive());
        card.setAvailable_limit(cardRequest.getLimit());
        return card;
    }

    public void favoriteMusic(Long userId, Long musicId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Music music = new Music(musicId, "god plan", 2.0);
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
//            Music music = verifyMusic(musicId);
            Music music = new Music(musicId, "god plan", 2.0);
            user.unfavoriteMusic(music, "Favorites");
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found");
        }
    }
//TODO Decidir sobre a musica se fica nesse projeto ou no outro

//    private Music verifyMusic(Long musicId) {
//        Music music = bandRepository.getMusic(musicId);
//        if (music == null) {
//            throw new RuntimeException("Não encontrei a música a ser favoritada");
//        }
//        return music;
//    }


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
