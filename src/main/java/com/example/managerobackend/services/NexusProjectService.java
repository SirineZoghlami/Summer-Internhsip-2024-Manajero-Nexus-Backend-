package com.example.managerobackend.services;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.repositories.NexusProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NexusProjectService {

    private static final Logger logger = LoggerFactory.getLogger(NexusProjectService.class);

    @Autowired
    private NexusProjectRepository repository;

    public List<NexusProject> getAllProjects() {
        logger.info("Fetching all projects");
        return repository.findAll();
    }

    public Optional<NexusProject> getProjectById(String id) {
        logger.info("Fetching project with ID: {}", id);
        return repository.findById(id);
    }

    public NexusProject createProject(NexusProject project) {
        logger.info("Creating new project: {}", project);
        // Validate the project data if necessary
        NexusProject createdProject = repository.save(project);
        logger.info("Project created with ID: {}", createdProject.getId());
        return createdProject;
    }

    public NexusProject updateProject(String id, NexusProject updatedProject) {
        logger.info("Updating project with ID: {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Project with ID: {} not found for update", id);
            return null; // Or throw an exception
        }
        updatedProject.setId(id);
        NexusProject updatedProj = repository.save(updatedProject);
        logger.info("Project updated with ID: {}", updatedProj.getId());
        return updatedProj;
    }

    public void deleteProject(String id) {
        logger.info("Deleting project with ID: {}", id);
        repository.deleteById(id);
        logger.info("Project deleted with ID: {}", id);
    }
}
