package com.example.managerobackend.repositories;

import com.example.managerobackend.models.SprintBacklog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintBacklogRepository extends MongoRepository<SprintBacklog, String> {
}
