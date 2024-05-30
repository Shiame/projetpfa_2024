package com.projectpfa.projetpfa.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Client extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client")
    private int idClient;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "num_tel")
    private String numTel;

    @Column(name = "id_vehicule")
    private int idVehicule;

    public Client() {
    }

    public Client(String nom, String prenom, String email, String motDePasse,
                  String adresse, String numTel, int idVehicule) {
        super(nom, prenom, email, motDePasse);
        this.adresse = adresse;
        this.numTel = numTel;
        this.idVehicule = idVehicule;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public int getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(int idVehicule) {
        this.idVehicule = idVehicule;
    }
}

