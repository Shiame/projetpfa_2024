package com.example.autocar.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TechInfoRequest {

    private int id;
    @NotBlank
    private String nom;
    @NotBlank
    private String prenom;
    @NotBlank
    private String email;
    @NotBlank
    private String motDePasse;

    private String specialite;

    private String disponibilite;

    private Long numTel;

    private Long salary;


    public TechInfoRequest(int idTechnicien, String nom, String prenom, String email, String specialite, String disponibilite, Long numTel, Long salary) {
        this.id = idTechnicien;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.specialite = specialite;
        this.disponibilite = disponibilite;
        this.numTel = numTel;
        this.salary = salary;
    }
}
