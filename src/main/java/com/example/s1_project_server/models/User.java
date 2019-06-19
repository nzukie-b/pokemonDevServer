package com.example.s1_project_server.models;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NaturalId
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    @ManyToMany(mappedBy = "users")
    private List<Pokemon> CollectedPokemon;

    public List<Pokemon> getCollectedPokemon() {
        return CollectedPokemon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setCollectedPokemon(List<Pokemon> collectedPokemon) {
        CollectedPokemon = collectedPokemon;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    private String dob;

    public void setAll(User user) {
        this.setDob(user.getDob());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setRole(user.getRole());
        this.setUsername(user.getUsername());
    }
}
