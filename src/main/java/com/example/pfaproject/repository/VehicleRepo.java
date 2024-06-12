package com.example.autocar.Repository;

import com.example.autocar.models.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByClientIdClient(Long idClient);


}
