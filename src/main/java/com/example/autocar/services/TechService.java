package com.example.autocar.services;


import com.example.autocar.Repository.TechnicienRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.exception.ResourceNotFoundException;
import com.example.autocar.models.Technicien;
import com.example.autocar.models.User;
import com.example.autocar.payload.response.TechInfoRequest;
import com.example.autocar.payload.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TechService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TechnicienRepo techRepo;


    // EDITING HIS OWN PROFILE


    //view Technicien profile
    public User getTechById(Long id) {
        Optional<User> optionalTech = userRepo.findById(id);
        if (optionalTech.isPresent() && optionalTech.get().getRole() == "TECHNICIEN") {
            return optionalTech.get();

        } else {
            throw new ResourceNotFoundException("Technicien not Found with id" + id);
        }
    }
    public Technicien addTechnicien(Technicien technicien) {
        // Save the User entity first to ensure it has an ID
        User user = technicien.getUser();
        user = userRepo.save(user);

        // Set the saved User entity to Technicien
        technicien.setUser(user);

        // Save the Technicien entity
        return techRepo.save(technicien);
    }


    //update Technicien profile
    public User updateTech(Long id, UserInfoResponse techUpdateResponse, TechInfoRequest request) {
        Optional<User> optionalTech = userRepo.findById(id);
        if (optionalTech.isPresent() && optionalTech.get().getRole() == "TECHNICIEN") {
            User tech = optionalTech.get();
            tech.setNom(techUpdateResponse.getNom());
            tech.setPrenom(techUpdateResponse.getPrenom());
            tech.setEmail(techUpdateResponse.getEmail());

            userRepo.save(tech);

            Technicien technicien = new Technicien();
            technicien.setDisponibilite(request.getDisponibilite());
            technicien.setSpecialite(request.getSpecialite());
            techRepo.save(technicien);

            return tech;


        } else {
            throw new RuntimeException("something went wrong! please try again!");
        }
    }




}
