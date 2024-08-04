package com.example.managerobackend.controllers;

import com.example.managerobackend.models.ProductBacklogItem;
import com.example.managerobackend.services.ProductBacklogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product-backlog-items")
public class ProductBacklogItemController {

    @Autowired
    private ProductBacklogItemService productBacklogItemService;

    @GetMapping
    public ResponseEntity<List<ProductBacklogItem>> getAllProductBacklogItems() {
        List<ProductBacklogItem> backlogItems = productBacklogItemService.getAllProductBacklogItems();
        return new ResponseEntity<>(backlogItems, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBacklogItem> getProductBacklogItemById(@PathVariable("id") String id) {
        Optional<ProductBacklogItem> backlogItem = productBacklogItemService.getProductBacklogItemById(id);
        return backlogItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ProductBacklogItem> createProductBacklogItem(@RequestBody ProductBacklogItem productBacklogItem) {
        ProductBacklogItem createdItem = productBacklogItemService.createOrUpdateProductBacklogItem(productBacklogItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBacklogItem> updateProductBacklogItem(@PathVariable("id") String id,
                                                                       @RequestBody ProductBacklogItem productBacklogItem) {
        // Optionally, you can handle the logic to update the item with the given ID
        // Here we assume that `createOrUpdateProductBacklogItem` will handle both creation and update
        productBacklogItem.setId(id);
        ProductBacklogItem updatedItem = productBacklogItemService.createOrUpdateProductBacklogItem(productBacklogItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBacklogItem(@PathVariable("id") String id) {
        productBacklogItemService.deleteProductBacklogItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
