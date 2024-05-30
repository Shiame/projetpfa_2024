package com.projectpfa.projetpfa.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotEmpty;


@MappedSuperclass
public class User {

	
	@Column(name="Last Name")
    private String nom;
	@Column(name="First Name")
    private String prenom;
	@Column(name="Email")
    @NotEmpty(message = "You need an email to acces")
    private String email;
    @Column(unique = true)
    private String motDePasse;

    public User(){

    }

    public User(String nom, String prenom, String email, String motDePasse){

        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.motDePasse=motDePasse;
    }

    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail(String email){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
    public String getMotDePasse(String motDePasse){
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse){
        this.motDePasse=motDePasse;
    }

	@Override
	public String toString() {
		return "User [ nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", motDePasse="
				+ motDePasse + "]";
	}


}
