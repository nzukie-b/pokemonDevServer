package com.example.s1_project_server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import java.util.*;

@Entity
public class Pokemon {
    @Id
    private long id;

    private String name;
    private String frontSprite;

    @ManyToMany
    @JsonIgnore
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "pokeId")
    private List<TrainingPokemon> children;


    public Pokemon() {
        super();
    }

    public Pokemon(long id) {
        super();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return getId() == pokemon.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Pokemon(long id, List<User> users) {
        this.id = id;
        this.users = users;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                '}';
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        if (users == null) {
            return new ArrayList<User>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrontSprite() {
        return frontSprite;
    }

    public void setFrontSprite(String frontSprite) {
        this.frontSprite = frontSprite;
    }

    public List<TrainingPokemon> getChildren() {
        return children;
    }

    public void setChildren(List<TrainingPokemon> children) {
        this.children = children;
    }

}
