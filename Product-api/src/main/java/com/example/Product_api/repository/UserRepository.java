
package com.example.Product_api.repository;

import com.example.Product_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // username se user fetch karne ke liye
    Optional<User> findByUsername(String username);

    // optional: check if username exists
    boolean existsByUsername(String username);
}
