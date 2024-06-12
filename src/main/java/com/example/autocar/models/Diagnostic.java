package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "diagnostic")
@Getter
@Setter
public class Diagnostic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idDiagnostic;

    @ManyToOne
    @JoinColumn(name = "idVehicle") // Correct reference to the Vehicle entity
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "idTechnicien") // Correct reference to the Technicien entity
    private Technicien technicien;

    @Column(name = "date_diagnostic")
    private Date dateDiagnostic;

    @Column(name = "desc_probleme")
    private String descProbleme;

    @Column(name = "etat_diagnostic")
    private String etatDiagnostic;

    @Column
    private Long estimatedPrice;

    @Column
    private Boolean clientApproved;

    public Diagnostic() {

    }







}
