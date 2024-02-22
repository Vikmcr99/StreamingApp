package com.example.my_streaming.Domain.Account.User;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/v1", produces = {"application/json"})
@Tag(name = "My_Streaming - UserController")

public class UserController {

    @Autowired
    private UserService service;

    @GetMapping(value="/users")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users =  service.getAllUsers();

        if (users == null || users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Long id) {
        try {
            User user = service.getById(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }

        catch (NoSuchElementException ns) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/users")
    public ResponseEntity<User> createAccount(@RequestBody User user){
        try{
            service.createUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }catch (Exception e) // Depois fazer um Tratamento de exceção global
        {
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
        } catch (Exception e) // Depois fazer um Tratamento de exceção global
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}

