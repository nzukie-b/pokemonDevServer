package com.example.s1_project_server.repositories;

import com.example.s1_project_server.models.Pokemon;
import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
}
