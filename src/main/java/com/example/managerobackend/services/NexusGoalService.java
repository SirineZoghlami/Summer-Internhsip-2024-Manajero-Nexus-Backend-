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

    public NexusGoal createNexusGoal(NexusGoal nexusGoal) {
        return nexusGoalRepository.save(nexusGoal);
    }

    public NexusGoal updateNexusGoal(String id, NexusGoal nexusGoal) {
        if (nexusGoalRepository.existsById(id)) {
            nexusGoal.setId(id); // Ensure the ID is set
            return nexusGoalRepository.save(nexusGoal);
        } else {
            throw new RuntimeException("Goal with ID " + id + " not found.");
        }
    }

    public void deleteNexusGoal(String id) {
        nexusGoalRepository.deleteById(id);
    }
}
