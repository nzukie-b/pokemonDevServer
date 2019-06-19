package com.example.s1_project_server.controllers;

import com.example.s1_project_server.models.Pokemon;
import com.example.s1_project_server.models.User;
import com.example.s1_project_server.models.UserType;
import com.example.s1_project_server.repositories.PokemonRepository;
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

	@PutMapping("/api/users/{userId}/pokemon/{pokeId}")
	public User addPokemonToCollection(@PathVariable(name = "userId") long userId,
			@PathVariable(name = "pokeId") long pokeId) {
		User u = ur.findById(userId).get();
		Pokemon p;
		if (pr.findById(pokeId).isPresent()) {
			p = pr.findById(pokeId).get();
		} else {
			p = new Pokemon(pokeId);
			pr.save(p);
		}
		ArrayList<Pokemon> newCollection = new ArrayList<Pokemon>(u.getCollectedPokemon());

		if (!newCollection.contains(p)) {
			newCollection.add(p);
			u.setCollectedPokemon(newCollection);
			System.out.println(newCollection);
			ur.save(u);

			ArrayList<User> newCollection2 = new ArrayList<User>(p.getUsers());
			newCollection2.add(u);
			p.setUsers(newCollection2);
			pr.save(p);
		}
		return findUserById(userId);
	}

//	@PutMapping("/api/users/{userId}/pokemonTeam/{pokeId}")
//	public User addPokemonToTeam(@PathVariable("userId") long userId, @PathVariable("pokeId") long pokeId) {
//		User u = ur.findById(userId).get();
//		if (u.getRole() == UserType.TRAINER) {
//			Pokemon p = pr.findById(pokeId).get();
//			if (u.getTeam().size() < 6) {
//				p.setTrainer(u);
//			}
//		}
//	}

	public UserRepository getUr() {
		return ur;
	}

	public void setUr(UserRepository ur) {
		this.ur = ur;
	}

	public PokemonRepository getPr() {
		return pr;
	}

	public void setPr(PokemonRepository pr) {
		this.pr = pr;
	}
}
