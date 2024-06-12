package com.example.autocar.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Technicien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idTechnicien;

    @Column(name = "specialite")
    private String specialite;

    @Column(name = "disponibilite")
    private String disponibilite;

    @Column(name = "num_tel")
    private Long numTel;

    @Column
    private Long salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Technicien() {

    }


}
