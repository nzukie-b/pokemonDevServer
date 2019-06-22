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

import java.util.List;

@RestController
@CrossOrigin(origins = { "http://localhost:3000", "*" })
public class TrainingPokemonController {

	@Autowired
	UserRepository ur;

	@Autowired
	PokemonRepository pr;

	@Autowired
	TrainingPokemonRepository trainingRepo;

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
		trainingRepo.deleteById(trainingPokeId);
		return trainingRepo.findTeamByUserId(userId);
	}

	@GetMapping("/api/users/{userId}/team")
	public List<TrainingPokemon> findAllTrainingPokemon(@PathVariable("userId") long userId) {
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
			@PathVariable("trainingPokeId") long trainingPokeId, @RequestBody TrainingPokemon updatedPokemon) {
		User u = ur.findById(userId).get();
		if (u.getRole() == UserRole.TRAINER && u.getTeam().size() <= 6) {
			TrainingPokemon tp = trainingRepo.findById(trainingPokeId).get();
			tp.setLevel(updatedPokemon.getLevel());
			trainingRepo.save(tp);
		}
		return trainingRepo.findTeamByUserId(userId);
	}
}
