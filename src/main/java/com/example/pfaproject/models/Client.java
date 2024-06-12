package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Client  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idClient;

    @Column
    private String adresse;

    @Column
    private String numTel;


    @OneToOne
    @JoinColumn( referencedColumnName = "id")
    private User user;

    public Client() {
    }




}

