package com.example.autocar.Controllers;


import com.example.autocar.Repository.ClientRepo;
import com.example.autocar.Repository.TechnicienRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.exception.ResourceNotFoundException;
import com.example.autocar.models.*;
import com.example.autocar.payload.request.UpdateClientRequest;
import com.example.autocar.payload.response.*;
import com.example.autocar.services.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ClientRepo clientRepo;
    @Autowired
    private TechnicienRepo techRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepo userRepo;




    // MANAGE APPOINTMENTS :
    @GetMapping("/rendezvous")
    public ResponseEntity<List<RendezVous>> getAllRendezVous() {
        List<RendezVous> rendezVousList = adminService.getAllRendezVous();
        return ResponseEntity.ok(rendezVousList);
    }

    @PostMapping("/approve-rendezvous/{id}")
    public ResponseEntity<MessageResponse> approveRendezVous(@PathVariable int id, @Valid @RequestBody RendezVousInfoResponse
            request) { try {
        adminService.approuveRendezVous(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Rendez-vous approved successfully"));
    } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred while approving the rendez-vous"));
            }
    }


    @PutMapping("/reject-rendezvous/{id}")
    public ResponseEntity<MessageResponse> rejectRendezVous(@PathVariable int id){
        try{
            adminService.rejectRendezVous(id);
            return ResponseEntity.ok(new MessageResponse("Rendez-vous rejected successfully!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("An error occurred while rejecting the rendez-vous"));

        }
    }

    //MANAGE CLIENTS

    //ALL clients
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return adminService.getAllClients();
    }


    //Admin adding new Client

    @PostMapping("/addClient")
    public ResponseEntity<MessageResponse> addClient(@Valid @RequestBody AddClientRequest request) {
        try {
            // Créez un nouveau compte utilisateur
            User user = new User(
                    request.getNom(),
                    request.getPrenom(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getMotDePasse())
            );

            user.setRole("CLIENT");

            userRepo.save(user);

            // Créez le client
            Client client = new Client();
            client.setAdresse(request.getAdresse());
            client.setNumTel(request.getNumTel());
            client.setUser(user); // Associez le client à l'utilisateur

            adminService.addNewClient(client);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Client added successfully"));

        } catch (Exception e) {
            String errorMessage = "Something went wrong: " + e.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(errorMessage));
        }
    }


    //Update Client profile

    @PutMapping("/updateclients/{id}")
    public ResponseEntity<MessageResponse> updateClient(@PathVariable int id, @RequestBody UpdateClientRequest updateClientRequest) {
        try {
            Client updatedClient = adminService.updateClientProfile(id, updateClientRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Client updated successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error updating client: " + e.getMessage()));
        }
    }


    //DELETE Client Profile

    @DeleteMapping("/deleteclient/{id}")
    public ResponseEntity<MessageResponse> deleteClient(@PathVariable int id) {
        try {
            Optional<Client> client = adminService.getClientById(id);
            if (client.isPresent()) {
                adminService.deleteClient(id);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Client deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Client not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Error deleting client: " + e.getMessage()));
        }
    }



    // MANAGE EMPLOYEES


    @GetMapping("/techniciens")
    public List<TechInfoRequest> getAllTechniciens() {
        return adminService.getAllTechniciens();
    }

    @PostMapping("/addTechnicien")
    public ResponseEntity<MessageResponse> addTechnicien(@Valid @RequestBody TechInfoRequest request) {
        try {
            // Create a new user's account
            User user = new User(
                    request.getNom(),
                    request.getPrenom(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getMotDePasse())
            );

            user.setRole("TECHNICIEN");

            userRepo.save(user);

            Technicien technicien = new Technicien();
            technicien.setSalary(request.getSalary());
            technicien.setSpecialite(request.getSpecialite());
            technicien.setDisponibilite(request.getDisponibilite());
            technicien.setUser(user);  // Set the user reference in technicien

            adminService.addNewTech(technicien);

            return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Technicien added successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MessageResponse("Error: " + e.getMessage()));
        }
    }

    //Update Technicien profile

    @PutMapping("/update-techniciens/{id}")
    public ResponseEntity<MessageResponse> updateTechnicien(@Valid @PathVariable int id, @Valid @RequestBody TechInfoRequest request) {
        Technicien updatedTech = adminService.updateTechProfile(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Technicien updated successfully"));
    }

    //DELETE Technicien Profile

    @DeleteMapping("/delete-technicien/{id}")
    public ResponseEntity<MessageResponse> deleteTechnicien(@PathVariable int id) {
        try {
            Optional<Technicien> technicien = adminService.getTechById(id);
            if(technicien.isPresent()) {
                adminService.deleteTech(id);
                return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Technicien deleted successfully"));

            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Technicien not found"));
            }

        } catch (Exception e) {
            String errorMessage = "Something went wrong while deleting the technicien";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(errorMessage));
        }

    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getAdminProfile(@PathVariable Long id) {
        try {
            User admin = adminService.getAdminById(id);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<MessageResponse> updateAdminProfile(@PathVariable Long id, @Valid @RequestBody UserInfoResponse request) {
        try {
            adminService.updateAdmin(id, request);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Your profile has been updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("An error occurred while updating your profile"));
        }
    }
}
