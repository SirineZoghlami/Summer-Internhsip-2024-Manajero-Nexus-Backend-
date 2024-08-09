package com.example.managerobackend.repositories;

import com.example.managerobackend.models.NexusProject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NexusProjectRepository extends MongoRepository<NexusProject, String> {
    // Custom query methods (if any) can be defined here
}
