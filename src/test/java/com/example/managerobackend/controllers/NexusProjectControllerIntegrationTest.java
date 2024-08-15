package com.example.managerobackend.controllers;

import com.example.managerobackend.models.NexusProject;
import com.example.managerobackend.services.NexusProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@WebMvcTest(NexusProjectController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class NexusProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NexusProjectService service;

    private NexusProject testProject;

    @BeforeEach
    public void setUp() {
        testProject = new NexusProject();
        testProject.setId("123");
        testProject.setProjectName("Test Project");
        testProject.setStartDate(new Date());
        testProject.setEndDate(new Date());
        testProject.setSprints(Collections.emptyList());
        testProject.setProductBacklog(Collections.emptyList());
        testProject.setGoals(Collections.emptyList());
    }

    @Test
    public void testGetAllProjects() throws Exception {
        when(service.getAllProjects()).thenReturn(Collections.singletonList(testProject));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].projectName").value("Test Project"));
    }

    @Test
    public void testGetProjectById() throws Exception {
        when(service.getProjectById("123")).thenReturn(testProject);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("Test Project"));
    }

    @Test
    public void testCreateProject() throws Exception {
        when(service.createProject(any(NexusProject.class))).thenReturn(testProject);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"projectName\":\"Test Project\", \"startDate\":\"2024-08-15\", \"endDate\":\"2024-12-31\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.projectName").value("Test Project"));
    }






    @Test
    public void testGetKpis() throws Exception {
        Map<String, Object> kpis = Map.of("KPI1", 100, "KPI2", 200);
        when(service.calculateKpis()).thenReturn(kpis);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/kpis")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.KPI1").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$.KPI2").value(200));
    }

    @Test
    public void testGetSprintsByProjectId() throws Exception {
        NexusProject.Sprint sprint = new NexusProject.Sprint();
        sprint.setNumber(1);
        List<NexusProject.Sprint> sprints = Collections.singletonList(sprint);
        when(service.getSprintsByProjectId("123")).thenReturn(sprints);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/123/sprints")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].number").value(1));
    }

    @Test
    public void testMarkSprintAsComplete() throws Exception {
        when(service.markSprintAsComplete("123", 1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/projects/123/sprints/1/complete")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




    @Test
    public void testAddReviewToSprint() throws Exception {
        NexusProject.Review review = new NexusProject.Review();
        review.setReviewContent("Test Review");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/projects/123/sprints/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Test Review\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testDeleteSprint() throws Exception {
        when(service.deleteSprint("123", 1)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/projects/123/sprints/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testGetPerformanceData() throws Exception {
        Map<String, Long> performanceData = Map.of("Performance1", 100L, "Performance2", 200L);
        when(service.getPerformanceData()).thenReturn(performanceData);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/performance")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Performance1").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Performance2").value(200L));
    }

    @Test
    public void testGetEfficiencyData() throws Exception {
        Map<String, Long> efficiencyData = Map.of("Efficiency1", 100L, "Efficiency2", 200L);
        when(service.getEfficiencyData()).thenReturn(efficiencyData);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/projects/efficiency")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Efficiency1").value(100L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Efficiency2").value(200L));
    }
}
