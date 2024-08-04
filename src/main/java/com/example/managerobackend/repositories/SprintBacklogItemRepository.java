package com.example.managerobackend.repositories;

import com.example.managerobackend.models.SprintBacklogItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintBacklogItemRepository extends MongoRepository<SprintBacklogItem, String> {
    // Custom query methods can be defined here if needed
}
