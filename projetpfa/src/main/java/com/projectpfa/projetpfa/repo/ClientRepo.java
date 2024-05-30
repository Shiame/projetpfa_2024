package com.projectpfa.projetpfa.repo;

import com.projectpfa.projetpfa.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Integer> {
    //to manage the data in the database(JpaRepo) so I can then delete or update
    //I can use this : Client FindByName(String name);


}
