package com.example.autocar.payload.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddClientRequest {
    // Champs pour les informations utilisateur
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String motDePasse;

    // Champs pour les informations client
    @NotNull
    private String adresse;
    @NotNull
    private String numTel;

    // Getters et setters
    // ...
}

