package com.example.managerobackend.services;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.repositories.NexusProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NexusProjectService {

    private static final Logger logger = LoggerFactory.getLogger(NexusProjectService.class);

    @Autowired
    private NexusProjectRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<NexusProject> getAllProjects() {
        logger.info("Fetching all projects");
        return repository.findAll();
    }

    public NexusProject getProjectById(String id) {
        logger.info("Fetching project with ID: {}", id);
        return repository.findById(id).orElse(null); // Return null if project not found
    }

    public NexusProject createProject(NexusProject project) {
        // Initialize new sprints with default 'completed' value
        for (NexusProject.Sprint sprint : project.getSprints()) {
            if (!sprint.isCompleted()) {
                sprint.setCompleted(false); // Default to false if not set
            }
        }
        logger.info("Creating new project: {}", project);
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

    public Map<String, Object> calculateKpis() {
        List<NexusProject> projects = mongoTemplate.findAll(NexusProject.class);

        // Total Number of Projects
        long totalProjects = projects.size();

        // Number of Projects per Status (Manual Counting)
        Map<String, Long> statusCount = new HashMap<>();
        for (NexusProject project : projects) {
            for (NexusProject.ProductBacklogItem item : project.getProductBacklog()) {
                statusCount.put(item.getStatus(), statusCount.getOrDefault(item.getStatus(), 0L) + 1);
            }
        }

        // Average Sprint Duration
        double averageSprintDuration = projects.stream()
                .flatMap(p -> p.getSprints().stream())
                .mapToLong(sprint -> {
                    if (sprint.getStartDate() != null && sprint.getEndDate() != null) {
                        long duration = sprint.getEndDate().getTime() - sprint.getStartDate().getTime();
                        return duration / (1000 * 60 * 60 * 24); // Convert duration to days
                    }
                    return 0; // Handle cases with null dates
                })
                .average()
                .orElse(0.0);

        // Total Number of Product Backlog Items
        long totalBacklogItems = projects.stream()
                .flatMap(p -> p.getProductBacklog().stream())
                .count();

        // Number of Goals per Project (Manual Counting)
        Map<String, Long> goalsCount = new HashMap<>();
        for (NexusProject project : projects) {
            goalsCount.put(project.getProjectName(), (long) project.getGoals().size());
        }

        // Aggregate results
        Map<String, Object> results = new HashMap<>();
        results.put("totalProjects", totalProjects);
        results.put("statusCount", statusCount);
        results.put("averageSprintDuration", averageSprintDuration);
        results.put("totalBacklogItems", totalBacklogItems);
        results.put("goalsCount", goalsCount);

        return results;
    }
    public List<NexusProject.Sprint> getSprintsByProjectId(String projectId) {
        logger.info("Fetching sprints for project with ID: {}", projectId);
        NexusProject project = repository.findById(projectId).orElse(null);
        return project != null ? project.getSprints() : null;
    }

    public List<NexusProject.NexusGoal> getGoalsByProjectId(String projectId) {
        logger.info("Fetching goals for project with ID: {}", projectId);
        NexusProject project = repository.findById(projectId).orElse(null);
        return project != null ? project.getGoals() : Collections.emptyList();
    }

    public boolean markSprintAsComplete(String projectId, int sprintNumber) {
        logger.info("Marking sprint number {} as complete for project with ID: {}", sprintNumber, projectId);
        NexusProject project = repository.findById(projectId).orElse(null);

        if (project != null) {
            for (NexusProject.Sprint sprint : project.getSprints()) {
                if (sprint.getNumber() == sprintNumber) {
                    sprint.setCompleted(true);
                    repository.save(project);
                    return true;
                }
            }
        }

        return false;
    }
    public NexusProject addProductBacklogItemToProject(String projectId, NexusProject.ProductBacklogItem backlogItem) {
        logger.info("Adding product backlog item to project with ID: {}", projectId);
        NexusProject project = repository.findById(projectId).orElse(null);

        if (project != null) {
            if (project.getProductBacklog() == null) {
                project.setProductBacklog(new ArrayList<>());
            }
            project.getProductBacklog().add(backlogItem);
            return repository.save(project);
        }

        return null;
    }

    public void addReviewToSprint(String projectId, int sprintNumber, NexusProject.Review review) {
        Optional<NexusProject> optionalProject = repository.findById(projectId);
        if (optionalProject.isPresent()) {
            NexusProject project = optionalProject.get();

            // Find the sprint by number
            Optional<NexusProject.Sprint> optionalSprint = project.getSprints().stream()
                    .filter(sprint -> sprint.getNumber() == sprintNumber)
                    .findFirst();

            if (optionalSprint.isPresent()) {
                NexusProject.Sprint sprint = optionalSprint.get();

                // Add the new review to the sprint's list of reviews
                List<NexusProject.Review> reviews = sprint.getReviews();
                if (reviews == null) {
                    reviews = new ArrayList<>();
                }
                reviews.add(review);
                sprint.setReviews(reviews);

                // Save the updated project
                repository.save(project);
            } else {
                // Handle case where sprint is not found
                throw new RuntimeException("Sprint not found with number: " + sprintNumber);
            }
        } else {
            // Handle case where project is not found
            throw new RuntimeException("Project not found with ID: " + projectId);
        }
    }
}