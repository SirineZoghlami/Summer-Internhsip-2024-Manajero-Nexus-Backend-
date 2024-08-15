package com.example.managerobackend.services;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.repositories.NexusProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class NexusProjectServiceIntegrationTest {

    @Autowired
    private NexusProjectService nexusProjectService;

    @Autowired
    private NexusProjectRepository nexusProjectRepository;

    private NexusProject testProject;

    @BeforeEach
    public void setUp() {
        // Clean up the collection before each test
        nexusProjectRepository.deleteAll();

        // Set up a test project
        testProject = new NexusProject();
        testProject.setProjectName("Test Project");
        testProject.setStartDate(new Date());
        testProject.setEndDate(new Date());
        testProject.setSprints(Arrays.asList(
                new NexusProject.Sprint(1, new Date(), new Date(), Collections.emptyList(), false),
                new NexusProject.Sprint(2, new Date(), new Date(), Collections.emptyList(), false)
        ));
        testProject.setProductBacklog(Arrays.asList(
                new NexusProject.ProductBacklogItem("1", "Item 1", "Description 1", "High", "In Progress"),
                new NexusProject.ProductBacklogItem("2", "Item 2", "Description 2", "Low", "Completed")
        ));
        testProject.setGoals(Collections.singletonList(new NexusProject.NexusGoal("Goal 1")));
    }

    @Test
    public void testCreateAndGetProject() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Get the project by ID
        NexusProject fetchedProject = nexusProjectService.getProjectById(createdProject.getId());

        // Verify the project
        assertNotNull(fetchedProject);
        assertEquals(createdProject.getId(), fetchedProject.getId());
        assertEquals("Test Project", fetchedProject.getProjectName());
    }

    @Test
    public void testUpdateProject() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Update the project
        createdProject.setProjectName("Updated Project Name");
        NexusProject updatedProject = nexusProjectService.updateProject(createdProject.getId(), createdProject);

        // Verify the update
        assertEquals("Updated Project Name", updatedProject.getProjectName());
    }

    @Test
    public void testDeleteProject() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Delete the project
        nexusProjectService.deleteProject(createdProject.getId());

        // Verify the project is deleted
        NexusProject deletedProject = nexusProjectService.getProjectById(createdProject.getId());
        assertNull(deletedProject);
    }


    @Test
    public void testGetSprintsByProjectId() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Get sprints by project ID
        List<NexusProject.Sprint> sprints = nexusProjectService.getSprintsByProjectId(createdProject.getId());

        // Verify sprints
        assertNotNull(sprints);
        assertEquals(2, sprints.size());
        assertEquals(1, sprints.get(0).getNumber());
    }

    @Test
    public void testGetGoalsByProjectId() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Get goals by project ID
        List<NexusProject.NexusGoal> goals = nexusProjectService.getGoalsByProjectId(createdProject.getId());

        // Verify goals
        assertNotNull(goals);
        assertEquals(1, goals.size());
        assertEquals("Goal 1", goals.get(0).getContent());
    }

    @Test
    public void testMarkSprintAsComplete() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Mark a sprint as complete
        boolean result = nexusProjectService.markSprintAsComplete(createdProject.getId(), 1);

        // Verify the result
        assertTrue(result);
        NexusProject updatedProject = nexusProjectService.getProjectById(createdProject.getId());
        assertTrue(updatedProject.getSprints().get(0).isCompleted());
    }

    @Test
    public void testAddProductBacklogItem() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Add a product backlog item
        NexusProject.ProductBacklogItem newItem = new NexusProject.ProductBacklogItem("3", "New Item", "Description 3", "Medium", "In Progress");
        NexusProject updatedProject = nexusProjectService.addProductBacklogItemToProject(createdProject.getId(), newItem);

        // Verify the backlog item is added
        assertNotNull(updatedProject);
        assertEquals(3, updatedProject.getProductBacklog().size());
        assertEquals("New Item", updatedProject.getProductBacklog().get(2).getTitle());
    }

    @Test
    public void testAddReviewToSprint() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Add a review to a sprint
        NexusProject.Review review = new NexusProject.Review(LocalDateTime.now(), "Review for Sprint 1");
        nexusProjectService.addReviewToSprint(createdProject.getId(), 1, review);

        // Verify the review is added
        NexusProject updatedProject = nexusProjectService.getProjectById(createdProject.getId());
        assertNotNull(updatedProject);
        assertEquals(1, updatedProject.getSprints().get(0).getReviews().size());
        assertEquals("Review for Sprint 1", updatedProject.getSprints().get(0).getReviews().get(0).getReviewContent());
    }

    @Test
    public void testDeleteSprint() {
        // Create the project
        NexusProject createdProject = nexusProjectService.createProject(testProject);

        // Delete a sprint
        boolean result = nexusProjectService.deleteSprint(createdProject.getId(), 1);

        // Verify the sprint is deleted
        assertTrue(result);
        NexusProject updatedProject = nexusProjectService.getProjectById(createdProject.getId());
        assertEquals(1, updatedProject.getSprints().size());
        assertEquals(2, updatedProject.getSprints().get(0).getNumber());
    }

    @Test
    public void testGetPerformanceData() {
        // Create a project
        nexusProjectService.createProject(testProject);

        // Get performance data
        Map<String, Long> performanceData = nexusProjectService.getPerformanceData();

        // Verify performance data
        assertEquals(1, performanceData.size());
        assertEquals(1, performanceData.get(testProject.getId()));
    }


}
