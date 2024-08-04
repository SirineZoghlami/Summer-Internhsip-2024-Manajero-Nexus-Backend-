package com.example.managerobackend.controllers;


import com.example.managerobackend.models.NexusGoal;
import com.example.managerobackend.services.NexusGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nexus-goals")
public class NexusGoalController {

    @Autowired
    private NexusGoalService nexusGoalService;

    @GetMapping
    public List<NexusGoal> getAllNexusGoals() {
        return nexusGoalService.getAllNexusGoals();
    }

    @GetMapping("/{id}")
    public Optional<NexusGoal> getNexusGoalById(@PathVariable String id) {
        return nexusGoalService.getNexusGoalById(id);
    }

    @PostMapping
    public NexusGoal createOrUpdateNexusGoal(@RequestBody NexusGoal nexusGoal) {
        return nexusGoalService.createOrUpdateNexusGoal(nexusGoal);
    }

    @DeleteMapping("/{id}")
    public void deleteNexusGoal(@PathVariable String id) {
        nexusGoalService.deleteNexusGoal(id);
    }
}

