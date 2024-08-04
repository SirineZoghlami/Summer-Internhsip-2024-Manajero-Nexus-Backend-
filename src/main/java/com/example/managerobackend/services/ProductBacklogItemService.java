package com.example.managerobackend.services;

import com.example.managerobackend.models.ProductBacklogItem;
import com.example.managerobackend.repositories.ProductBacklogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogItemService {

    @Autowired
    private ProductBacklogItemRepository productBacklogItemRepository;

    public List<ProductBacklogItem> getAllProductBacklogItems() {
        return productBacklogItemRepository.findAll();
    }

    public Optional<ProductBacklogItem> getProductBacklogItemById(String id) {
        return productBacklogItemRepository.findById(id);
    }

    public ProductBacklogItem createOrUpdateProductBacklogItem(ProductBacklogItem productBacklogItem) {
        return productBacklogItemRepository.save(productBacklogItem);
    }

    public void deleteProductBacklogItem(String id) {
        productBacklogItemRepository.deleteById(id);
    }
}
