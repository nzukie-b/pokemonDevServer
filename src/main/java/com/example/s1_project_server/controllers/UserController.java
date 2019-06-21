package com.example.s1_project_server.controllers;

import com.example.s1_project_server.models.User;
import com.example.s1_project_server.repositories.PokemonRepository;
import com.example.s1_project_server.repositories.TrainingPokemonRepository;
import com.example.s1_project_server.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "*" })
public class UserController {

	@Autowired
	UserRepository ur;

	@Autowired
	PokemonRepository pr;

	@Autowired
	TrainingPokemonRepository trainingRepo;

	@GetMapping("/api/users")
	public List<User> findAllUsers() {
		return (List<User>) ur.findAll();
	}

	@GetMapping("/api/users/{userId}")
	public User findUserById(@PathVariable("userId") long id) {
		if (ur.findById(id).isPresent()) {
			return ur.findById(id).get();
		} else {
			return null;
		}
	}

	@PostMapping("/api/users")
	public User addUser(@RequestBody User newUser) {
		newUser.setCollectedPokemon(new ArrayList<>());
		newUser.setTeam(new ArrayList<>());
		return ur.save(newUser);
	}

	@PutMapping("/api/users/{userId}")
	public User updateUser(@PathVariable long userId, @RequestBody User user) {
		User u = ur.findById(userId).get();
		u.setAll(user);
		ur.save(u);
		return findUserById(userId);
	}

	@DeleteMapping("/api/users/{userId}")
	public List<User> deleteUser(@PathVariable long userId) {
		ur.deleteById(userId);
		return findAllUsers();
	}

	@GetMapping("/api/users/username/{username}/password/{password}")
	public User findUserByCredentials(@PathVariable("username") String username,
			@PathVariable("password") String password) {
		return ur.findUserByCredentials(username, password);
	}
	
	@GetMapping("/api/users/username/{username}")
	public User findUserByUsername(@PathVariable("username") String username) {
		return ur.findUserByUsername(username);
	}

}
