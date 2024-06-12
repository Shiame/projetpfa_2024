package com.example.autocar.Repository;


import com.example.autocar.models.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RendezVousRepo extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByAssignedTechIdTechnicien(int idTechnicien);
}
