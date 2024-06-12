package com.example.autocar.services;


import com.example.autocar.Repository.ClientRepo;
import com.example.autocar.Repository.RendezVousRepo;
import com.example.autocar.Repository.TechnicienRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.exception.ResourceNotFoundException;
import com.example.autocar.models.*;

import com.example.autocar.payload.request.UpdateClientRequest;
import com.example.autocar.payload.response.RendezVousInfoResponse;
import com.example.autocar.payload.response.TechInfoRequest;
import com.example.autocar.payload.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private ClientRepo clientRepo;

    @Autowired
    private TechnicienRepo technicienRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TechnicienRepo techRepo;

    @Autowired
    private RendezVousRepo rendezVousRepo;



    //MANAGE APPOINTMENTS IN THE DASHBOARD

    public List<RendezVous> getAllRendezVous() {
        return  rendezVousRepo.findAll();
    }


    // approuve rendez vous

    public RendezVous approuveRendezVous(long id, RendezVousInfoResponse request){
        Optional<RendezVous> optionalRendezVous = rendezVousRepo.findById(id);
        if(optionalRendezVous.isPresent()){
            RendezVous rendezVous = optionalRendezVous.get();
            Optional<Technicien> optionalTechnicien=technicienRepo.findByName(request.getTechnicienNom());
                rendezVous.setAssignedTech(optionalTechnicien.get());
                rendezVous.setEstimatedPrice(request.getEstimadtedPrice());
                rendezVous.setStatutRendezVous("Approved");
                return rendezVousRepo.save(rendezVous);
            } else {
                throw new RuntimeException("Rendez Vous n'existe pas");
        }
    }

    //Reject Rendez-Vous

    public RendezVous rejectRendezVous(long id){
        Optional<RendezVous> optionalRendezVous = rendezVousRepo.findById(id);
        if(optionalRendezVous.isPresent()){
            RendezVous rendezVous = optionalRendezVous.get();
            rendezVous.setStatutRendezVous("Rejected");
            return rendezVousRepo.save(rendezVous);
        } else {
            throw new RuntimeException("Rendez Vous n'existe pas");
        }

    }





    //MANAGE CLIENTS PAGE:

    public List<Client> getAllClients() {
        List<Client> clients = clientRepo.findAll();
        for (Client client : clients) {
            User user = client.getUser();
            System.out.println("Client ID: " + client.getIdClient());
            System.out.println("Nom: " + user.getNom());
            System.out.println("Prenom: " + user.getPrenom());
            System.out.println("Email: " + user.getEmail());
            System.out.println("NumTel: " + client.getNumTel());
            System.out.println("Adresse: " + client.getAdresse());
            System.out.println("Role: " + user.getRole());
        }
        return clients;
    }

    public Optional<Client> getClientById(int id) {
        return clientRepo.findById(id);
    }

    public Client addNewClient(Client client) {
        return clientRepo.save(client);
    }

    public void deleteClient(int id) {
        clientRepo.deleteById(id);
    }

    public Client updateClientProfile(int id, UpdateClientRequest updateClientRequest) {
        Optional<Client> clientOptional = clientRepo.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            User user = client.getUser();
            user.setNom(updateClientRequest.getNom());
            user.setPrenom(updateClientRequest.getPrenom());
            user.setEmail(updateClientRequest.getEmail());
            userRepo.save(user);
            client.setAdresse(updateClientRequest.getAdresse());
            client.setNumTel(updateClientRequest.getNumTel());
            return clientRepo.save(client);
        } else {
            throw new ResourceNotFoundException("Client not found with id " + id);
        }
    }




    //MANAGE EMPLOYEES PAGE  :

    public List<TechInfoRequest> getAllTechniciens() {
        List<Technicien> techniciens = technicienRepo.findAll();
        return techniciens.stream().map(tech -> new TechInfoRequest(
                tech.getIdTechnicien(),
                tech.getUser().getNom(),
                tech.getUser().getPrenom(),
                tech.getUser().getEmail(),
                tech.getSpecialite(),
                tech.getDisponibilite(),
                tech.getNumTel(),
                tech.getSalary()
        )).collect(Collectors.toList());
    }

    public Optional<Technicien> getTechById(int id) {
        return techRepo.findById(id);
    }

    public Technicien addNewTech(Technicien technicien) {
        return techRepo.save(technicien);
    }

    public void deleteTech(int id) {
        techRepo.deleteById(id);
    }

    public Technicien updateTechProfile(int id, TechInfoRequest techDetails) {
        Optional<Technicien> techOptional = techRepo.findById(id);
        if (techOptional.isPresent()) {
            Technicien technicien = techOptional.get();
            User user = technicien.getUser();
            user.setNom(techDetails.getNom());
            user.setPrenom(techDetails.getPrenom());
            user.setEmail(techDetails.getEmail());
            userRepo.save(user);
            technicien.setDisponibilite(techDetails.getDisponibilite());
            technicien.setSpecialite(techDetails.getSpecialite());
            technicien.setSalary(techDetails.getSalary());
            return techRepo.save(technicien);
        } else {
            throw new ResourceNotFoundException("Technician not Found with id" + id);
        }
    }



    // EDITING HIS OWN PROFILE


    //view Admin profile
    public User getAdminById(Long id){
        Optional<User> optionalAdmin = userRepo.findById(id);
        if(optionalAdmin.isPresent() && optionalAdmin.get().getRole()=="ADMIN"){
            return optionalAdmin.get();

        } else {
            throw new ResourceNotFoundException("Admin not Found with id" + id);
        }
    }


    //update Admin profile
    public User updateAdmin(Long id, UserInfoResponse adminUpdateResponse){
        Optional<User> optionalAdmin = userRepo.findById(id);
        if (optionalAdmin.isPresent() && optionalAdmin.get().getRole()== "ADMIN") {
            User admin = optionalAdmin.get();
            admin.setNom(adminUpdateResponse.getNom());
            admin.setPrenom(adminUpdateResponse.getPrenom());
            admin.setEmail(adminUpdateResponse.getEmail());
            if(adminUpdateResponse.getMotDePasse() != null
            && !adminUpdateResponse.getMotDePasse().isEmpty()){
                admin.setMotDePasse(passwordEncoder.encode(adminUpdateResponse.getMotDePasse()));
            } return  userRepo.save(admin);
        } else {
            throw new RuntimeException("something went wrong! please try again!");
        }

    }




}
