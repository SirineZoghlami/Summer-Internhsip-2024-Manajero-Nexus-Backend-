package com.example.managerobackend.controllers;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.services.NexusProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
        NexusProject project = service.getProjectById(id);
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
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
        return project != null ? ResponseEntity.ok(project) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        logger.info("Handling DELETE request to delete project with ID: {}", id);
        service.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/kpis")
    public ResponseEntity<?> getKpis() {
        logger.info("Handling GET request to fetch KPIs");
        try {
            Map<String, Object> kpis = service.calculateKpis();
            return ResponseEntity.ok(kpis);
        } catch (Exception e) {
            logger.error("Error fetching KPIs: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error fetching KPIs");
        }
    }

    @GetMapping("/{id}/sprints")
    public ResponseEntity<List<NexusProject.Sprint>> getSprintsByProjectId(@PathVariable String id) {
        logger.info("Handling GET request to fetch sprints for project with ID: {}", id);
        List<NexusProject.Sprint> sprints = service.getSprintsByProjectId(id);
        return sprints != null ? ResponseEntity.ok(sprints) : ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}/sprints/{number}/complete")
    public ResponseEntity<Void> markSprintAsComplete(@PathVariable String id, @PathVariable int number) {
        logger.info("Handling PATCH request to mark sprint number {} as complete for project with ID: {}", number, id);
        boolean success = service.markSprintAsComplete(id, number);
        return success ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/goals")
    public ResponseEntity<List<NexusProject.NexusGoal>> getGoalsByProjectId(@PathVariable("id") String projectId) {
        List<NexusProject.NexusGoal> goals = service.getGoalsByProjectId(projectId);
        if (goals.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(goals);
    }
    @PostMapping("/{id}/backlog-items")
    public ResponseEntity<NexusProject> addProductBacklogItemToProject(@PathVariable String id, @RequestBody NexusProject.ProductBacklogItem backlogItem) {
        logger.info("Handling POST request to add a product backlog item to project with ID: {}", id);
        NexusProject updatedProject = service.addProductBacklogItemToProject(id, backlogItem);
        return updatedProject != null ? ResponseEntity.ok(updatedProject) : ResponseEntity.notFound().build();
    }

}
