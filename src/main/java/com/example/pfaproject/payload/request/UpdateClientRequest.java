package com.example.autocar.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientRequest {
    private String nom;
    private String prenom;
    private String email;
    private String numTel;
    private String adresse;

    // Getters and setters
}
