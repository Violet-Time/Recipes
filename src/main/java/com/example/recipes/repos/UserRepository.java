package com.example.recipes.repos;

import org.springframework.data.repository.CrudRepository;
import com.example.recipes.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
