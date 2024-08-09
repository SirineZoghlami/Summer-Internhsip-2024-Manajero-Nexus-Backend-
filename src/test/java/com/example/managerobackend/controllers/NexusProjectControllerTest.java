package com.example.managerobackend.controllers;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.services.NexusProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class NexusProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NexusProjectService service;

    @InjectMocks
    private NexusProjectController controller;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testGetAllProjects() throws Exception {
        // Arrange
        NexusProject project = new NexusProject(); // Create a mock project
        when(service.getAllProjects()).thenReturn(Collections.singletonList(project));

        // Act & Assert
        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", notNullValue()));
    }

    @Test
    public void testGetProjectById() throws Exception {
        // Arrange
        NexusProject project = new NexusProject(); // Create a mock project
        when(service.getProjectById("1")).thenReturn(project);

        // Act & Assert
        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testCreateProject() throws Exception {
        // Arrange
        NexusProject project = new NexusProject(); // Create a mock project
        when(service.createProject(any(NexusProject.class))).thenReturn(project);

        // Act & Assert
        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"New Project\" }")) // Update with appropriate fields
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testUpdateProject() throws Exception {
        // Arrange
        NexusProject project = new NexusProject(); // Create a mock project
        when(service.updateProject(eq("1"), any(NexusProject.class))).thenReturn(project);

        // Act & Assert
        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Updated Project\" }")) // Update with appropriate fields
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testDeleteProject() throws Exception {
        // Arrange
        doNothing().when(service).deleteProject("1");

        // Act & Assert
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetKpis() throws Exception {
        // Arrange
        when(service.calculateKpis()).thenReturn(Map.of("kpi1", 100));

        // Act & Assert
        mockMvc.perform(get("/api/projects/kpis"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.kpi1", is(100)));
    }
}
