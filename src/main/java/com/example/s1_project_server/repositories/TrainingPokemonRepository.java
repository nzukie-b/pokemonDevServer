package com.example.s1_project_server.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.s1_project_server.models.TrainingPokemon;

public interface TrainingPokemonRepository extends CrudRepository<TrainingPokemon, Long> {
	@Query("SELECT tp from TrainingPokemon AS tp WHERE tp.pokeId.id=:pokeId AND tp.userId.id=:userId")
	public TrainingPokemon findByUserAndPokemon(
			@Param("pokeId") long pokeId, 
			@Param("userId") long userId);
}
