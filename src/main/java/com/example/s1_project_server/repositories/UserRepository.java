package com.example.s1_project_server.repositories;

import com.example.s1_project_server.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("select user from User user where user.username=:username and user.password=:password")
    public User findUserByCredentials(
            @Param("username") String username,
            @Param("password") String password);

}
