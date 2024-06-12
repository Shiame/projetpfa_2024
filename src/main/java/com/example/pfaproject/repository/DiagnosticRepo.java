package com.example.autocar.Repository;

import com.example.autocar.models.Diagnostic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosticRepo extends JpaRepository<Diagnostic, Integer> {
    List<Diagnostic> findByTechnicienIdTechnicien(int idTechnicien);
    @Query("SELECT d FROM Diagnostic d WHERE d.vehicle.client.idClient = :clientId")
    List<Diagnostic> findByClientId(@Param("clientId") int clientId);

}