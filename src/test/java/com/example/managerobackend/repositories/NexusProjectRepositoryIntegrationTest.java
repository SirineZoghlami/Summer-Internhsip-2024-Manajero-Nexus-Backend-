package com.example.managerobackend.repositories;

import com.example.managerobackend.models.NexusProject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
public class NexusProjectRepositoryIntegrationTest {

    @Autowired
    private NexusProjectRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private NexusProject testProject;

    @BeforeEach
    public void setUp() {
        // Clean up the collection before each test
        mongoTemplate.dropCollection(NexusProject.class);

        // Set up a test project
        testProject = new NexusProject();
        testProject.setProjectName("Test Project");
        testProject.setDescription("A description for the test project");
        testProject.setStartDate(new Date());
        testProject.setEndDate(new Date());
        // Add other fields if necessary
    }

    @Test
    public void testSaveAndFindById() {
        // Save the test project
        NexusProject savedProject = repository.save(testProject);

        // Find by ID
        NexusProject foundProject = repository.findById(savedProject.getId()).orElse(null);

        // Verify the saved project is the same as the found project
        assertNotNull(foundProject);
        assertEquals(savedProject.getId(), foundProject.getId());
        assertEquals(savedProject.getProjectName(), foundProject.getProjectName());
    }

    @Test
    public void testFindAll() {
        // Save multiple projects
        repository.save(testProject);

        // Verify we can find the saved projects
        List<NexusProject> projects = repository.findAll();
        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        assertEquals(testProject.getProjectName(), projects.get(0).getProjectName());
    }

    @Test
    public void testDeleteById() {
        // Save the test project
        NexusProject savedProject = repository.save(testProject);

        // Delete the project by ID
        repository.deleteById(savedProject.getId());

        // Verify the project is deleted
        NexusProject deletedProject = repository.findById(savedProject.getId()).orElse(null);
        assertNull(deletedProject);
    }
}
