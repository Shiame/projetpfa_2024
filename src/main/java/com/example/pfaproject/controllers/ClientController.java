package com.example.autocar.Controllers;



import com.example.autocar.models.*;
import com.example.autocar.payload.response.ClientInfoRequest;
import com.example.autocar.payload.response.MessageResponse;
import com.example.autocar.payload.response.RendezVousInfoResponse;
import com.example.autocar.payload.response.UserInfoResponse;
import com.example.autocar.services.ClientService;
import com.example.autocar.services.DiagnosticService;
import com.example.autocar.services.RendezVousService;
import com.example.autocar.services.RepairService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
@PreAuthorize("hasRole('CLIENT')")
public class ClientController {


    @Autowired
    private ClientService clientService;

    @Autowired
    private RendezVousService rendezVousService;

    @Autowired
    private DiagnosticService diagnosticService;
    @Autowired
    private RepairService repairService;


    // view his profile


    @GetMapping("/profile/{id}")
    public ResponseEntity<User> getProfile(@PathVariable Long id) {
        try{
            User client  = clientService.getClientById(id);
            return ResponseEntity.ok(client);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // View his profile details


    @GetMapping("/profileDetails/{id}")
    public ResponseEntity<Client> getClientDetails(@PathVariable int id) {
        try {
            Client clientDetails = clientService.getClientDetailsById(id);
            return ResponseEntity.ok(clientDetails);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    //Update his own profile


    @PutMapping("/profile/{id}")
    public ResponseEntity<MessageResponse> updateClientProfile(@PathVariable Long id, @Valid @RequestBody UserInfoResponse userInfoResponse, @Valid @RequestBody ClientInfoRequest clientInfoRequest) {
        try {
            User updatedClient = clientService.updateClientProfile(id, userInfoResponse, clientInfoRequest);
            return ResponseEntity.ok(new MessageResponse("Profile updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("An error occurred while updating the profile"));
        }
    }


    // BOOK A APPOINTMENT

    @PostMapping("/book-rendezvous")
    public ResponseEntity<RendezVous> bookRendezVous(@Valid @RequestBody RendezVousInfoResponse rendezVousRequest) {
        RendezVous newRendezVous = rendezVousService.bookRendezVous(rendezVousRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRendezVous);
    }




    @GetMapping("/diagnostics/{clientId}")
    public ResponseEntity<List<Diagnostic>> getClientDiagnostics(@PathVariable int clientId) {
        List<Diagnostic> diagnostics = diagnosticService.getDiagnosticsByClientId(clientId);
        return ResponseEntity.ok(diagnostics);
    }

    @GetMapping("/repairs/{clientId}")
    public ResponseEntity<List<Repair>> getClientRepairs(@PathVariable long clientId) {
        List<Repair> repairs = repairService.getRepairsByClientId(clientId);
        return ResponseEntity.ok(repairs);
    }

    //Endpoint to approve/reject the estimated price
    @PutMapping("/diagnostics/{idDiagnostic}/approve")
    public ResponseEntity<Diagnostic> approveEstimate(@PathVariable int idDiagnostic, @RequestParam boolean approve) {
        Optional<Diagnostic> optionalDiagnostic = diagnosticService.getDiagnosticById(idDiagnostic);

        if (!optionalDiagnostic.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Diagnostic diagnostic = optionalDiagnostic.get();
        if (approve) {
            diagnostic.setClientApproved(true);
            diagnostic.setEtatDiagnostic("APPROVED");
        } else {
            diagnostic.setClientApproved(false);
            diagnostic.setEtatDiagnostic("REJECTED");
        }

        Diagnostic updatedDiagnostic = diagnosticService.updateDiagnostic(diagnostic);
        return ResponseEntity.ok(updatedDiagnostic);
    }



}















