package com.example.s1_project_server.controllers;

import com.example.s1_project_server.models.User;
import com.example.s1_project_server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "*"})
public class UserController {

    @Autowired
    UserRepository ur;


    @GetMapping("/users")
    public List<User> findAllUsers() {
        return (List<User>) ur.findAll();
    }

    @GetMapping("/users/{userId}")
    public User fingUserById(@PathVariable("userId") long id) {
        if (ur.findById(id).isPresent()) {
            return ur.findById(id).get();
        } else {
            return null;
        }
    }

    @PostMapping("/users")
    public List<User> addUser(@RequestBody User newUser) {
        ur.save(newUser);
        return findAllUsers();
    }

    @PutMapping("/users/{userId}")
    public User updateUser(@PathVariable long userId,
                                 @RequestBody User user) {
        User u = ur.findById(userId).get();
        u.setAll(user);
        ur.save(u);
        return fingUserById(userId);
    }

    @DeleteMapping("/users/{userId}")
    public List<User> deleteUser(@PathVariable long userId){
        ur.deleteById(userId);
        return findAllUsers();
    }

    @GetMapping("/users/username/{username}/password/{password}")
    public User findUserByCredentials(
            @PathVariable("username") String username,
            @PathVariable("password") String password) {
        return ur.findUserByCredentials(username, password);
    }
}
