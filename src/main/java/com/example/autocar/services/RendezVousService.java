package com.example.autocar.services;

import com.example.autocar.Repository.ClientRepo;
import com.example.autocar.Repository.RendezVousRepo;
import com.example.autocar.Repository.UserRepo;
import com.example.autocar.Repository.VehicleRepo;
import com.example.autocar.exception.ResourceNotFoundException;
import com.example.autocar.models.Client;
import com.example.autocar.models.RendezVous;
import com.example.autocar.models.Vehicle;
import com.example.autocar.payload.response.RendezVousInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RendezVousService {

    private final RendezVousRepo rendezVousRepo;
    private final VehicleRepo vehicleRepo;
    private final UserRepo userRepo;
   @Autowired
   private ClientRepo clientRepo;

    @Autowired
    public RendezVousService(RendezVousRepo rendezVousRepo, VehicleRepo vehicleRepo, UserRepo userRepo) {
        this.rendezVousRepo = rendezVousRepo;
        this.vehicleRepo = vehicleRepo;
        this.userRepo = userRepo;
    }

    public List<RendezVous> getRendezVousByIdTechnicien(int idTechnicien) {
        return rendezVousRepo.findByAssignedTechIdTechnicien(idTechnicien);
    }

    public Optional<RendezVous> getRendezVousById(long idRendezVous) {
        return rendezVousRepo.findById(idRendezVous);
    }


    public RendezVous bookRendezVous(RendezVousInfoResponse request) {
        Client client = clientRepo.findById(Math.toIntExact(request.getClientId()))
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + request.getClientId()));

        Vehicle vehicle = vehicleRepo.findById(request.getVehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + request.getVehicleId()));

        RendezVous rendezVous = new RendezVous();
        rendezVous.setClient(client);
        rendezVous.setVehicle(vehicle);
        rendezVous.setDateRendezVous(request.getDateRendezVous());
        rendezVous.setCommentaire(request.getCommentaire());
        rendezVous.setStatutRendezVous("PENDING");

        return rendezVousRepo.save(rendezVous);
    }

}
