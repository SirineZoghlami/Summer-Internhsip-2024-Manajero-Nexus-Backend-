package com.example.managerobackend.services;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.repositories.NexusProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class NexusProjectServiceTest {

    @InjectMocks
    private NexusProjectService nexusProjectService;

    @Mock
    private NexusProjectRepository repository;

    @Mock
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProjects() {
        // Arrange
        NexusProject project = new NexusProject();
        project.setId("1");
        when(repository.findAll()).thenReturn(Arrays.asList(project));

        // Act
        List<NexusProject> result = nexusProjectService.getAllProjects();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetProjectById() {
        // Arrange
        NexusProject project = new NexusProject();
        project.setId("1");
        when(repository.findById(anyString())).thenReturn(Optional.of(project));

        // Act
        NexusProject result = nexusProjectService.getProjectById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    public void testCreateProject() {
        // Arrange
        NexusProject project = new NexusProject();
        project.setId("1");
        when(repository.save(any(NexusProject.class))).thenReturn(project);

        // Act
        NexusProject result = nexusProjectService.createProject(project);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    public void testUpdateProject() {
        // Arrange
        NexusProject existingProject = new NexusProject();
        existingProject.setId("1");
        NexusProject updatedProject = new NexusProject();
        updatedProject.setId("1");
        when(repository.existsById(anyString())).thenReturn(true);
        when(repository.save(any(NexusProject.class))).thenReturn(updatedProject);

        // Act
        NexusProject result = nexusProjectService.updateProject("1", updatedProject);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(repository).save(updatedProject);
    }

    @Test
    public void testUpdateProjectNotFound() {
        // Arrange
        NexusProject updatedProject = new NexusProject();
        when(repository.existsById(anyString())).thenReturn(false);

        // Act
        NexusProject result = nexusProjectService.updateProject("1", updatedProject);

        // Assert
        assertNull(result);
    }

    @Test
    public void testDeleteProject() {
        // Act
        nexusProjectService.deleteProject("1");

        // Assert
        verify(repository).deleteById("1");
    }


}
