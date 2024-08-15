package com.example.managerobackend.repositories;


import com.example.managerobackend.models.Tutorial;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
}
