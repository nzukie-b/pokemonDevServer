package com.example.s1_project_server.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.s1_project_server.models.TrainingPokemon;

public interface TrainingPokemonRepository extends CrudRepository<TrainingPokemon, Long> {

}
