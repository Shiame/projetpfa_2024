package com.projectpfa.projetpfa.repo;

import com.projectpfa.projetpfa.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface adminRepo extends JpaRepository<Admin, Integer> {
}
