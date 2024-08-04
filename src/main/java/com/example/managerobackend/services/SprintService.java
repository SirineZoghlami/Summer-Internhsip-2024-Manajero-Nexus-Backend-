package com.example.managerobackend.services;

import com.example.managerobackend.models.Sprint;
import com.example.managerobackend.repositories.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    @Autowired
    private SprintRepository sprintRepository;

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Optional<Sprint> getSprintById(String id) {
        return sprintRepository.findById(id);
    }

    public Sprint createOrUpdateSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public void deleteSprint(String id) {
        sprintRepository.deleteById(id);
    }
}
