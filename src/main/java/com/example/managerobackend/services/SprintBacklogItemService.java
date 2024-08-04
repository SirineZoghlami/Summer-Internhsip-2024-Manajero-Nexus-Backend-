package com.example.managerobackend.services;

import com.example.managerobackend.models.SprintBacklogItem;
import com.example.managerobackend.repositories.SprintBacklogItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintBacklogItemService {

    @Autowired
    private SprintBacklogItemRepository sprintBacklogItemRepository;

    public List<SprintBacklogItem> getAllSprintBacklogItems() {
        return sprintBacklogItemRepository.findAll();
    }

    public Optional<SprintBacklogItem> getSprintBacklogItemById(String id) {
        return sprintBacklogItemRepository.findById(id);
    }

    public SprintBacklogItem createOrUpdateSprintBacklogItem(SprintBacklogItem sprintBacklogItem) {
        return sprintBacklogItemRepository.save(sprintBacklogItem);
    }

    public void deleteSprintBacklogItem(String id) {
        sprintBacklogItemRepository.deleteById(id);
    }
}
