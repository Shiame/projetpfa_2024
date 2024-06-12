package com.example.autocar.services;


import com.example.autocar.Repository.DiagnosticRepo;
import com.example.autocar.Repository.VehicleRepo;
import com.example.autocar.models.Diagnostic;
import com.example.autocar.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnosticService {
    @Autowired
    private DiagnosticRepo diagnosticRepository;

    @Autowired
    private VehicleRepo vehicleRepo;

    public Diagnostic saveDiagnostic(Diagnostic diagnostic) {
        return diagnosticRepository.save(diagnostic);
    }

    public Diagnostic updateDiagnostic(Diagnostic diagnostic) {
        return diagnosticRepository.save(diagnostic);
    }

    public List<Diagnostic> getDiagnosticsByTechnicienId(int idTechnicien) {
        return diagnosticRepository.findByTechnicienIdTechnicien(idTechnicien);
    }

    public Optional<Diagnostic> getDiagnosticById(int idDiagnostic) {
        return diagnosticRepository.findById(idDiagnostic);
    }

    public List<Diagnostic> getDiagnosticsByClientId(Long clientId) {

        List<Vehicle> vehicles = vehicleRepo.findByClientIdClient(clientId);
        List<Diagnostic> diagnostics = new ArrayList<>();


        for (Vehicle vehicle : vehicles) {
            diagnostics.addAll(diagnosticRepository.findByClientId(vehicle.getIdVehicle()));
        }
        return diagnostics;
    }
    public List<Diagnostic> getDiagnosticsByClientId(int clientId) {
        return diagnosticRepository.findByClientId(clientId);
    }

}
