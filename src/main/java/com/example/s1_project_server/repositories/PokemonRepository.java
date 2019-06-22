package com.example.s1_project_server.repositories;

import com.example.s1_project_server.models.Pokemon;
import com.example.s1_project_server.models.TrainingPokemon;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

    @Query("SELECT pokemon from Pokemon pokemon WHERE pokemon.name=:name")
    public List<Pokemon> findPokemonByName(
            @Param("name") String name);
}
