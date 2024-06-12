package com.example.autocar.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousInfoResponse {

    private Long clientId;
    private Long vehicleId;
    private Date dateRendezVous;
    private String vehicleInfo; // Assuming vehicleInfo is a string containing necessary vehicle data
    private String commentaire;
    private Double estimadtedPrice;
    private String technicienNom;

}
