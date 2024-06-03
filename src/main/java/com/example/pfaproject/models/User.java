package com.example.pfaproject.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "Email")})
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name="Last Name")
    private String nom;
	@Column(name="First Name")
    private String prenom;
	@Column(name="Email")
    private String email;

    @NotBlank
    @Column(name="Password")
    private String motDePasse;

    @Column(length = 25)
    private String role;

    public User(){

    }

    public User(String nom, String prenom, String email, String motDePasse){

        this.nom=nom;
        this.prenom=prenom;
        this.email=email;
        this.motDePasse=motDePasse;
    }



	@Override
	public String toString() {
		return "User [ nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", motDePasse="
				+ motDePasse + "]";
	}


}
