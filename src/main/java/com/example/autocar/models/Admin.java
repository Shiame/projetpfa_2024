package com.example.autocar.models;
import jakarta.persistence.*;

@Entity
@Table
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int idAdmin;

    @OneToOne
    @JoinColumn( referencedColumnName = "id")
    private User user;


    public Admin(int id , User user) {
        this.user = user;
        this.idAdmin = id;
    }


    public Admin() {

    }
}
