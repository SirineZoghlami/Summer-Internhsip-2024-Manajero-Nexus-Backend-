package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Project;
import com.example.managerobackend.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get all projects
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    // Get a project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Optional<Project> project = projectService.getProjectById(id);
        return project.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Create a new project
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        try {
            Project createdProject = projectService.createProject(project);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Update an existing project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable String id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(id, project);
            return ResponseEntity.ok(updatedProject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Delete a project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
