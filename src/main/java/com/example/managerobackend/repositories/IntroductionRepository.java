package com.example.managerobackend.repositories;

import com.example.managerobackend.models.Introduction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IntroductionRepository extends MongoRepository<Introduction, String> {
}
