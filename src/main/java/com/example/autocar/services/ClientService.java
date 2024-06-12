package com.example.autocar.services;



import com.example.autocar.Repository.ClientRepo;
import com.example.autocar.Repository.RendezVousRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.Repository.VehicleRepo;
import com.example.autocar.exception.ResourceNotFoundException;
import com.example.autocar.models.Client;
import com.example.autocar.models.RendezVous;
import com.example.autocar.models.User;
import com.example.autocar.models.Vehicle;
import com.example.autocar.payload.response.ClientInfoRequest;
import com.example.autocar.payload.response.RendezVousInfoResponse;
import com.example.autocar.payload.response.UserInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ClientRepo clientRepository;

    @Autowired
    private RendezVousRepo rendezVousRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VehicleRepo vehicleRepo;

    public User getClientById(Long id){
        Optional<User> client = userRepository.findById(id);
        if(client.isPresent() && client.get().getRole().equals("CLIENT")){
            return client.get();
        } else {
            throw new ResourceNotFoundException("Client not found with id" + id );
        }

    }

    public Client getClientDetailsById(int id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        } else {
            throw new ResourceNotFoundException("Client details not found with id " + id);
        }
    }


    public User updateClientProfile(Long id, UserInfoResponse userInfoResponse, ClientInfoRequest clientInfoRequest) {
        Optional<User> optionalClient = userRepository.findById(id);
        if (optionalClient.isPresent() && optionalClient.get().getRole().equals("CLIENT")) {
            User client = optionalClient.get();
            client.setNom(userInfoResponse.getNom());
            client.setPrenom(userInfoResponse.getPrenom());
            client.setEmail(userInfoResponse.getEmail());
            if (userInfoResponse.getMotDePasse() != null && !userInfoResponse.getMotDePasse().isEmpty()) {
                client.setMotDePasse(passwordEncoder.encode(userInfoResponse.getMotDePasse()));
            }
            userRepository.save(client);

            Client clientDetails = getClientDetailsById(Math.toIntExact(id));
            clientDetails.setNumTel(clientInfoRequest.getNumTel());
            clientDetails.setAdresse(clientInfoRequest.getAdresse());
            clientRepository.save(clientDetails);

            return client;
        } else {
            throw new ResourceNotFoundException("Client not found with id " + id);
        }
    }



}
