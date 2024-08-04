package com.example.managerobackend.controllers;

import com.example.managerobackend.models.SprintBacklogItem;
import com.example.managerobackend.services.SprintBacklogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sprint-backlog")
public class SprintBacklogItemController {

    @Autowired
    private SprintBacklogItemService sprintBacklogItemService;

    @GetMapping
    public ResponseEntity<List<SprintBacklogItem>> getAllSprintBacklogItems() {
        List<SprintBacklogItem> items = sprintBacklogItemService.getAllSprintBacklogItems();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SprintBacklogItem> getSprintBacklogItemById(@PathVariable("id") String id) {
        Optional<SprintBacklogItem> item = sprintBacklogItemService.getSprintBacklogItemById(id);
        return item.map(i -> new ResponseEntity<>(i, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SprintBacklogItem> createSprintBacklogItem(@RequestBody SprintBacklogItem sprintBacklogItem) {
        SprintBacklogItem createdItem = sprintBacklogItemService.createOrUpdateSprintBacklogItem(sprintBacklogItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SprintBacklogItem> updateSprintBacklogItem(@PathVariable("id") String id,
                                                                     @RequestBody SprintBacklogItem sprintBacklogItem) {
        sprintBacklogItem.setId(id);
        SprintBacklogItem updatedItem = sprintBacklogItemService.createOrUpdateSprintBacklogItem(sprintBacklogItem);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprintBacklogItem(@PathVariable("id") String id) {
        sprintBacklogItemService.deleteSprintBacklogItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
