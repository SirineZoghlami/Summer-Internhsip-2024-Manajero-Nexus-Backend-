package com.example.managerobackend.services;

import com.example.managerobackend.models.SprintBacklog;
import com.example.managerobackend.repositories.SprintBacklogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SprintBacklogService {

    @Autowired
    private SprintBacklogRepository sprintBacklogRepository;

    public SprintBacklog createSprintBacklog(SprintBacklog sprintBacklog) {
        return sprintBacklogRepository.save(sprintBacklog);
    }

    public List<SprintBacklog> getAllSprintBacklogs() {
        return sprintBacklogRepository.findAll();
    }

    public SprintBacklog getSprintBacklogById(String id) {
        Optional<SprintBacklog> optionalBacklog = sprintBacklogRepository.findById(id);
        return optionalBacklog.orElse(null);
    }

    public SprintBacklog updateSprintBacklog(String id, SprintBacklog sprintBacklog) {
        if (sprintBacklogRepository.existsById(id)) {
            sprintBacklog.setId(id);
            return sprintBacklogRepository.save(sprintBacklog);
        }
        return null;
    }

    public void deleteSprintBacklog(String id) {
        if (sprintBacklogRepository.existsById(id)) {
            sprintBacklogRepository.deleteById(id);
        }
    }

    public SprintBacklog updateStatus(String id, String status) {
        Optional<SprintBacklog> optionalBacklog = sprintBacklogRepository.findById(id);
        if (optionalBacklog.isPresent()) {
            SprintBacklog backlog = optionalBacklog.get();
            backlog.setStatus(status);
            return sprintBacklogRepository.save(backlog);
        }
        return null;
    }
}
