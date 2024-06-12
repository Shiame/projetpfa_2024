package com.example.autocar.Controllers;


import com.example.autocar.Repository.DiagnosticRepo;
import com.example.autocar.models.Diagnostic;
import com.example.autocar.models.RendezVous;
import com.example.autocar.models.Repair;
import com.example.autocar.models.User;
import com.example.autocar.payload.request.DiagnosticRequest;
import com.example.autocar.payload.request.RepairUpdateRequest;
import com.example.autocar.payload.request.UpdateDiagnosticRequest;
import com.example.autocar.payload.response.MessageResponse;
import com.example.autocar.payload.response.TechInfoRequest;
import com.example.autocar.payload.response.UserInfoResponse;
import com.example.autocar.services.DiagnosticService;
import com.example.autocar.services.RendezVousService;
import com.example.autocar.services.RepairService;
import com.example.autocar.services.TechService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/technicien")
@PreAuthorize("hasRole('TECHNICIEN')")

public class TechController {

    @Autowired
    private RendezVousService rendezVousService;
    @Autowired
    private DiagnosticRepo diagnosticRepo;
    @Autowired
    private DiagnosticService diagnosticService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private TechService techService;



    // Endpoint to view assigned rendezvous
    @GetMapping("/assigned/{idTechnicien}")
    public ResponseEntity<List<RendezVous>> getAssignedRendezVous(@PathVariable int idTechnicien) {
        List<RendezVous> rendezVousList=rendezVousService.getRendezVousByIdTechnicien(idTechnicien);
        return ResponseEntity.ok(rendezVousList);
    }

    // add a diagnostic for the assigned rendez vous :
    @PostMapping("/add-diagnostic")
    public ResponseEntity<Diagnostic> addDiagnostic(@PathVariable int idRendezVous, @RequestBody DiagnosticRequest diagnosticRequest) {
        Optional<RendezVous> rendezVous=rendezVousService.getRendezVousById(idRendezVous);
        if (rendezVous.isEmpty() || rendezVous.get().getAssignedTech().getIdTechnicien() != diagnosticRequest.getIdTechnicien()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setVehicle(diagnosticRequest.getVehicle());
        diagnostic.setDateDiagnostic(new Date());
        diagnostic.setDescProbleme(diagnosticRequest.getDescProbleme());
        diagnostic.setEtatDiagnostic("IN_PROGRESS");

        Diagnostic savedDiagnostic = diagnosticService.saveDiagnostic(diagnostic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDiagnostic);
    }


    // endpoint to update diagnostic
    @PutMapping("/diagnostics/{idDiagnostic}")
    public ResponseEntity<Diagnostic> updateDiagnostic(@PathVariable int diagnosticId, @Valid @RequestBody UpdateDiagnosticRequest diagnosticUpdateRequest) {
        Optional<Diagnostic> optionalDiagnostic = diagnosticService.getDiagnosticById(diagnosticId);

        if (!optionalDiagnostic.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnostic diagnostic = optionalDiagnostic.get();

        if (diagnostic.getTechnicien().getIdTechnicien() != diagnosticUpdateRequest.getIdTechnicien()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        diagnostic.setEtatDiagnostic(diagnosticUpdateRequest.getEtatDiagnostic());
        diagnostic.setDescProbleme(diagnosticUpdateRequest.getDescProbleme());
        diagnostic.setEstimatedPrice(diagnosticUpdateRequest.getEstimatedPrice());

        Diagnostic updatedDiagnostic = diagnosticService.updateDiagnostic(diagnostic);
        return ResponseEntity.ok(updatedDiagnostic);
    }


    // REPAIR


    // endpoint to add a repair
    @PostMapping("/repairs")
    public ResponseEntity<Repair> addRepair(@RequestBody Repair repair,@RequestBody RepairUpdateRequest request) {
        Repair addedRepair = repairService.addRepair(repair,request);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedRepair);
    }

    // endpoint to update repair status
    @PutMapping("/repairs/{repairId}")
    public ResponseEntity<Repair> updateRepair(@PathVariable int repairId, @RequestBody RepairUpdateRequest repairUpdateRequest) {
        Repair updatedRepair = repairService.updateRepair(repairId, repairUpdateRequest);
        return ResponseEntity.ok(updatedRepair);
    }


    //TECHNICIEN MANAGING HIS OWN PROFILE
    //ADMIN MANAGING HIS OWN P7ROFILE

    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getTechProfile(@PathVariable Long id) {
        try{
            User admin = techService.getTechById(id);
            return ResponseEntity.ok(admin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<MessageResponse> updateTechProfile(@PathVariable Long id, @Valid @RequestBody UserInfoResponse request1, @Valid @RequestBody TechInfoRequest request2) {
        try {
            techService.updateTech(id, request1, request2);
            return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("your profile has been updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("An error occurred while updating yur profile"));
        }
    }


}






