package com.example.managerobackend.repositories;

import com.example.managerobackend.models.NexusIntegrationTeam;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NexusIntegrationTeamRepository extends MongoRepository<NexusIntegrationTeam, String> {
}