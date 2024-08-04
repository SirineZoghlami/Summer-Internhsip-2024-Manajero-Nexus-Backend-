package com.example.managerobackend.services;


import com.example.managerobackend.models.NexusGoal;
import com.example.managerobackend.repositories.NexusGoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NexusGoalService {

    @Autowired
    private NexusGoalRepository nexusGoalRepository;

    public List<NexusGoal> getAllNexusGoals() {
        return nexusGoalRepository.findAll();
    }

    public Optional<NexusGoal> getNexusGoalById(String id) {
        return nexusGoalRepository.findById(id);
    }

    public NexusGoal createOrUpdateNexusGoal(NexusGoal nexusGoal) {
        return nexusGoalRepository.save(nexusGoal);
    }

    public void deleteNexusGoal(String id) {
        nexusGoalRepository.deleteById(id);
    }
}
