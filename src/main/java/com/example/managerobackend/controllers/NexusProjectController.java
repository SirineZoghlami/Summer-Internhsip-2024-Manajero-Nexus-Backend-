package com.example.managerobackend.controllers;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.services.NexusProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class NexusProjectController {

    private static final Logger logger = LoggerFactory.getLogger(NexusProjectController.class);

    @Autowired
    private NexusProjectService service;

    @GetMapping
    public List<NexusProject> getAllProjects() {
        logger.info("Handling GET request to fetch all projects");
        return service.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NexusProject> getProjectById(@PathVariable String id) {
        logger.info("Handling GET request to fetch project with ID: {}", id);
        Optional<NexusProject> project = service.getProjectById(id);
        return project.map(ResponseEntity::ok).orElseGet(() -> {
            logger.warn("Project with ID: {} not found", id);
            return ResponseEntity.notFound().build();
        });
    }

    @PostMapping
    public ResponseEntity<NexusProject> createProject(@RequestBody NexusProject project) {
        logger.info("Handling POST request to create a new project: {}", project);
        try {
            NexusProject createdProject = service.createProject(project);
            return ResponseEntity.ok(createdProject);
        } catch (Exception e) {
            logger.error("Error creating project: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<NexusProject> updateProject(@PathVariable String id, @RequestBody NexusProject updatedProject) {
        logger.info("Handling PUT request to update project with ID: {}", id);
        NexusProject project = service.updateProject(id, updatedProject);
        if (project == null) {
            logger.warn("Project with ID: {} not found for update", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        logger.info("Handling DELETE request to delete project with ID: {}", id);
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }


}
