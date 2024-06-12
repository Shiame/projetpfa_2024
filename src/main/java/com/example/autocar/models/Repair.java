package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Repair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idReparation;


    @ManyToOne
    @JoinColumn( referencedColumnName = "idTechnicien")
    private Technicien technicien;

    @ManyToOne
    @JoinColumn( referencedColumnName = "idDiagnostic")
    private Diagnostic diagnostic;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idVehicle")
    private Vehicle vehicle;

    @Column(name = "cout_rep")
    private int coutRep;

    @Column(name = "duree_rep")
    private int dureeRep;

    @Column(name = "etat_rep")
    private String etatRep;

    public Repair() {
        // Default constructor
    }



}
