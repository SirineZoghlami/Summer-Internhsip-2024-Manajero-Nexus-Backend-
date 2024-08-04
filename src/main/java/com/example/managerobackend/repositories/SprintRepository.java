package com.example.managerobackend.repositories;

import com.example.managerobackend.models.Sprint;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends MongoRepository<Sprint, String> {
}
