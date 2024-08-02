package com.example.managerobackend.controllers;

import com.example.managerobackend.models.SprintBacklog;
import com.example.managerobackend.services.SprintBacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprintbacklogs")
public class SprintBacklogController {

    @Autowired
    private SprintBacklogService sprintBacklogService;

    @PostMapping
    public SprintBacklog createSprintBacklog(@RequestBody SprintBacklog sprintBacklog) {
        return sprintBacklogService.createSprintBacklog(sprintBacklog);
    }

    @GetMapping
    public List<SprintBacklog> getAllSprintBacklogs() {
        return sprintBacklogService.getAllSprintBacklogs();
    }

    @GetMapping("/{id}")
    public SprintBacklog getSprintBacklogById(@PathVariable String id) {
        return sprintBacklogService.getSprintBacklogById(id);
    }

    @PutMapping("/{id}")
    public SprintBacklog updateSprintBacklog(@PathVariable String id, @RequestBody SprintBacklog sprintBacklog) {
        return sprintBacklogService.updateSprintBacklog(id, sprintBacklog);
    }

    @PatchMapping("/{id}/status")
    public SprintBacklog updateSprintBacklogStatus(@PathVariable String id, @RequestParam String status) {
        return sprintBacklogService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteSprintBacklog(@PathVariable String id) {
        sprintBacklogService.deleteSprintBacklog(id);
    }
}
