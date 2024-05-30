package com.projectpfa.projetpfa.service;

import com.projectpfa.projetpfa.model.Client;

import java.util.Collection;

public interface ClientService {
    Client create(Client client);
    Collection<Client> list(int limit);
    Client get(int id);
    Client update(Client client);
    Boolean delete(int id);
}
