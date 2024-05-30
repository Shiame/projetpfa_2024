package com.projectpfa.projetpfa.service.implementation;

import com.projectpfa.projetpfa.model.Client;
import com.projectpfa.projetpfa.repo.ClientRepo;
import com.projectpfa.projetpfa.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService {
    private final ClientRepo clientRepo;

    @Override
    public Client create(Client client) {
        log.info("Saving new server: {}", client.getNom());
        return null;
    }

    @Override
    public Collection<Client> list(int limit) {
        return List.of();
    }

    @Override
    public Client get(int id) {
        return null;
    }

    @Override
    public Client update(Client client) {
        return null;
    }

    @Override
    public Boolean delete(int id) {
        return null;
    }
}
