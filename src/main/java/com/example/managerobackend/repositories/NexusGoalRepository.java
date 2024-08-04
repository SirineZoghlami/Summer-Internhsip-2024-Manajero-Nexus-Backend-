package com.example.managerobackend.repositories;

import com.example.managerobackend.models.NexusGoal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NexusGoalRepository extends MongoRepository<NexusGoal, String> {
}