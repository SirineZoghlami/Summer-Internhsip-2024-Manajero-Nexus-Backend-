package com.example.managerobackend.repositories;

import com.example.managerobackend.models.ProductBacklogItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBacklogItemRepository extends MongoRepository<ProductBacklogItem, String> {
}
