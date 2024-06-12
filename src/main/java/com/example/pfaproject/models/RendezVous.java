package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idRendezVous;

    @Column
    private Date dateRendezVous;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idVehicle")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(referencedColumnName = "idClient")
    private Client client;

    @ManyToOne
    @JoinColumn (referencedColumnName = "idTechnicien")
    private Technicien assignedTech;

    @OneToOne
    @JoinColumn(referencedColumnName = "idAdmin")
    private Admin admin;

    @Column
    private String statutRendezVous;

    @Column
    private Double estimatedPrice;

    @Column
    private String commentaire;





}
