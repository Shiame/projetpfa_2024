package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column
 private int idVehicle;

 @ManyToOne
 @JoinColumn( referencedColumnName = "idClient")
 private Client client;

 @Column
 private String marque;

 @Column
 private String modele;

 @Column
 private String category;

 @Column
 private String etatVehicule;

 @Column
 private String problemDesc;



}