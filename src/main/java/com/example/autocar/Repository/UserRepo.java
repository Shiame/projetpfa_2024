package com.example.autocar.Repository;

import com.example.autocar.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String Email);
    public List<User> findByRole(String Role);

    Boolean existsByEmail(String Email);
}

