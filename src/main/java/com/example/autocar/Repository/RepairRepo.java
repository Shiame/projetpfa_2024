package com.example.autocar.Repository;



import com.example.autocar.models.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RepairRepo extends JpaRepository<Repair, Integer> {
    @Query("SELECT d FROM Diagnostic d WHERE d.vehicle.client.idClient = :clientId")
    Collection<? extends Repair> findByClientId(@Param("clientId") int clientId);
}
