package com.example.s1_project_server.repositories;

import com.example.s1_project_server.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
