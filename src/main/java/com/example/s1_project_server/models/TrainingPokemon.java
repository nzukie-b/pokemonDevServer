package com.example.s1_project_server.models;

import javax.persistence.*;

@Entity
public class TrainingPokemon {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Pokemon pokeId;
	
	private int level;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Pokemon getPokeId() {
		return pokeId;
	}

	public void setPokeId(Pokemon pokeId) {
		this.pokeId = pokeId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@ManyToOne
	private User userId;
	

}
