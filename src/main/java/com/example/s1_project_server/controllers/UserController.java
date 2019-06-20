package com.example.s1_project_server.controllers;

import com.example.s1_project_server.models.Pokemon;
import com.example.s1_project_server.models.TrainingPokemon;
import com.example.s1_project_server.models.User;
import com.example.s1_project_server.models.UserRole;
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

	@PostMapping("/api/users/{userId}/team/{pokeId}")
	public List<TrainingPokemon> addPokemonToTeam(@PathVariable("userId") long userId,
			@PathVariable("pokeId") long pokeId) {
		User u = ur.findById(userId).get();
		if (u.getRole() == UserRole.TRAINER && u.getTeam().size() < 6) {
			Pokemon p = pr.findById(pokeId).get();
			TrainingPokemon tp = new TrainingPokemon();
			tp.setPokeId(p);
			tp.setUserId(u);
			trainingRepo.save(tp);
		}
		return trainingRepo.findTeamByUserId(userId);
	}

	@DeleteMapping("/api/users/{userId}/team/{trainingPokeId}")
	public List<TrainingPokemon> deletePokemonFromTeam(@PathVariable("userId") long userId,
			@PathVariable("trainingPokeId") long trainingPokeId) {
		User u = ur.findById(userId).get();
		trainingRepo.deleteById(trainingPokeId);
		return trainingRepo.findTeamByUserId(userId);
	}
	
	@GetMapping("/api/users/{userId}/team")
	public List<TrainingPokemon> findAllTrainingPokemon(@PathVariable("userId") long userId) {
		User u = ur.findById(userId).get();
		return trainingRepo.findTeamByUserId(userId);
	}
	
	@GetMapping("/api/users/{userId}/team/{trainingPokeId}")
	public TrainingPokemon findTrainingPokemonByUserAndPokemon(@PathVariable("userId") long userId,
			@PathVariable("trainingPokeId") long trainingPokeId) {
		if (trainingRepo.existsById(trainingPokeId)) {
			 return trainingRepo.findById(trainingPokeId).get();
		}
		return null;
	}
	
	@PutMapping("/api/users/{userId}/team/{trainingPokeId}")
	public List<TrainingPokemon> updatePokemonOnTeam(@PathVariable("userId") long userId,
			@PathVariable("trainingPokeId") long trainingPokeId,
			@RequestBody TrainingPokemon updatedPokemon) {
		User u = ur.findById(userId).get();
		if (u.getRole() == UserRole.TRAINER && u.getTeam().size() < 6) {
			TrainingPokemon tp = trainingRepo.findById(trainingPokeId).get();
			tp.setLevel(updatedPokemon.getLevel());
			trainingRepo.save(tp);
		}
		return trainingRepo.findTeamByUserId(userId);
	}
}
