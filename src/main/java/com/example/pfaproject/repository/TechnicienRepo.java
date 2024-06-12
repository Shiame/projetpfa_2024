package com.example.autocar.Repository;

import com.example.autocar.models.Technicien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TechnicienRepo extends JpaRepository<Technicien, Integer> {

    List<Technicien> findAll();
    @Query("SELECT t FROM Technicien t JOIN t.user u WHERE u.nom = :technicienNom")
    Optional<Technicien> findByName(@Param("technicienNom") String technicienNom);


}
