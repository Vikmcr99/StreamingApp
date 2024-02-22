package com.example.my_streaming.Domain.Account.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional(readOnly = true)
    public User getById(Long id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.orElse(null);
    }

    public List<User> getAllUsers(){
        return repository.findAll();
    }

    public void createUser(User user){
        repository.save(user);
    }

    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

}
