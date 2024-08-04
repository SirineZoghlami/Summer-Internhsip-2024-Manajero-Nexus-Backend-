package com.example.managerobackend.services;

import com.example.managerobackend.models.Project;
import com.example.managerobackend.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Optional<Project> getProjectById(String id) {
        return projectRepository.findById(id);
    }

    // Create a new project
    public Project createProject(Project project) {
        // Validate if the project is new
        if (project.getId() != null && projectRepository.existsById(project.getId())) {
            throw new IllegalArgumentException("Project with this ID already exists.");
        }
        return projectRepository.save(project);
    }

    // Update an existing project
    public Project updateProject(String id, Project updatedProject) {
        // Check if the project exists
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project with this ID does not exist.");
        }
        // Ensure the ID in updatedProject matches the ID provided
        updatedProject.setId(id);
        return projectRepository.save(updatedProject);
    }

    // Delete a project
    public void deleteProject(String id) {
        if (!projectRepository.existsById(id)) {
            throw new IllegalArgumentException("Project with this ID does not exist.");
        }
        projectRepository.deleteById(id);
    }
}
