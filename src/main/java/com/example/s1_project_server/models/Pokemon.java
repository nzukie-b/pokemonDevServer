package com.example.s1_project_server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
public class Pokemon {
    @Id
    private long id;
    @ManyToMany
    @JsonIgnore
    private List<User> users;

}
