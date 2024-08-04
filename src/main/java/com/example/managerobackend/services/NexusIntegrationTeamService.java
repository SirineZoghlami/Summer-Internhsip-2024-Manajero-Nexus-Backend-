package com.example.managerobackend.services;

import com.example.managerobackend.models.NexusIntegrationTeam;
import com.example.managerobackend.repositories.NexusIntegrationTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NexusIntegrationTeamService {

    @Autowired
    private NexusIntegrationTeamRepository nexusIntegrationTeamRepository;

    public List<NexusIntegrationTeam> getAllNexusIntegrationTeams() {
        return nexusIntegrationTeamRepository.findAll();
    }

    public Optional<NexusIntegrationTeam> getNexusIntegrationTeamById(String id) {
        return nexusIntegrationTeamRepository.findById(id);
    }

    public NexusIntegrationTeam createNexusIntegrationTeam(NexusIntegrationTeam nexusIntegrationTeam) {
        return nexusIntegrationTeamRepository.save(nexusIntegrationTeam);
    }

    public NexusIntegrationTeam updateNexusIntegrationTeam(String id, NexusIntegrationTeam nexusIntegrationTeam) {
        if (nexusIntegrationTeamRepository.existsById(id)) {
            nexusIntegrationTeam.setId(id);
            return nexusIntegrationTeamRepository.save(nexusIntegrationTeam);
        } else {
            throw new RuntimeException("Team not found with id: " + id);
        }
    }

    public void deleteNexusIntegrationTeam(String id) {
        nexusIntegrationTeamRepository.deleteById(id);
    }
}
