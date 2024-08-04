package com.example.managerobackend.repositories;

import com.example.managerobackend.models.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
}
