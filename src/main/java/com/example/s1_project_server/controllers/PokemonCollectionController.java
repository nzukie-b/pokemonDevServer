package com.example.s1_project_server.controllers;

import com.example.s1_project_server.models.Pokemon;
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
public class PokemonCollectionController {

	@Autowired
	UserRepository ur;

	@Autowired
	PokemonRepository pr;

	@Autowired
	TrainingPokemonRepository trainingRepo;

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
		return ur.findById(userId).get();
	}
	
	@DeleteMapping("/api/users/{userId}/pokemon/{pokeId}")
	public User removePokemonFromCollection(
			@PathVariable(name = "userId") long userId,
			@PathVariable(name = "pokeId") long pokeId) {
		User u = ur.findById(userId).get();
		Pokemon p = pr.findById(pokeId).get();
		u.getCollectedPokemon().remove(p);
		ur.save(u);
		return u;
	}
	
	@GetMapping("/api/users/{userId}/pokemon")
	public List<Pokemon> findPokemonInCollection(@PathVariable(name = "userId") long userId) {
		User u = ur.findById(userId).get();
		return u.getCollectedPokemon();
	}

}
