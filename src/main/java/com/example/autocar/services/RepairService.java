package com.example.autocar.services;


import com.example.autocar.Repository.RepairRepo;
import com.example.autocar.Repository.VehicleRepo;
import com.example.autocar.models.Repair;
import com.example.autocar.models.Vehicle;
import com.example.autocar.payload.request.RepairUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RepairService {
    @Autowired
    private RepairRepo repairRepo;

    @Autowired
    private VehicleRepo vehicleRepository;

    public Repair addRepair(Repair repair, RepairUpdateRequest repairUpdateRequest) {
        repair.setEtatRep("IN_PROGRESS");
        repair.setDureeRep(repairUpdateRequest.getDureeRep());
        return repairRepo.save(repair);
    }

    public Repair updateRepair(int repairId, RepairUpdateRequest repairUpdateRequest) {
        Optional<Repair> optionalRepair = repairRepo.findById(repairId);

        if (!optionalRepair.isPresent()) {
            throw new IllegalArgumentException("Invalid Repair");
        }

        Repair repair = optionalRepair.get();
        repair.setEtatRep(repairUpdateRequest.getEtatRep());
        repair.setCoutRep(repairUpdateRequest.getCoutRep());

        return repairRepo.save(repair);
    }





    public List<Repair> getRepairsByClientId(Long clientId) {
        List<Vehicle> vehicles = vehicleRepository.findByClientIdClient(clientId);
        List<Repair> repairs = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            repairs.addAll(repairRepo.findByClientId(vehicle.getIdVehicle()));
        }
        return repairs;
    }

}
