package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TutorialControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TutorialService tutorialService;

    @InjectMocks
    private TutorialController tutorialController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(tutorialController).build();
    }

    @Test
    public void testSaveTutorial() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial(); // Create a mock tutorial
        when(tutorialService.saveTutorial(any(Tutorial.class), any(), any())).thenReturn(tutorial);

        MockMultipartFile tutorialFile = new MockMultipartFile("tutorial", "", "application/json", "{ \"name\": \"Test Tutorial\" }".getBytes());
        MockMultipartFile roleImageFile = new MockMultipartFile("roleImage", "roleImage.png", "image/png", "image content".getBytes());
        MockMultipartFile processImageFile = new MockMultipartFile("processImage", "processImage.png", "image/png", "image content".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/api/tutorials/create")
                        .file(tutorialFile)
                        .file(roleImageFile)
                        .file(processImageFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testGetAllTutorials() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial(); // Create a mock tutorial
        when(tutorialService.getAllTutorials()).thenReturn(Collections.singletonList(tutorial));

        // Act & Assert
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]", notNullValue()));
    }

    @Test
    public void testGetTutorialById() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial(); // Create a mock tutorial
        when(tutorialService.getTutorialById("1")).thenReturn(tutorial);

        // Act & Assert
        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testUpdateTutorial() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial(); // Create a mock tutorial
        when(tutorialService.updateTutorial(eq("1"), any(Tutorial.class), any(), any())).thenReturn(tutorial);

        MockMultipartFile tutorialFile = new MockMultipartFile("tutorial", "", "application/json", "{ \"name\": \"Updated Tutorial\" }".getBytes());
        MockMultipartFile roleImageFile = new MockMultipartFile("roleImage", "roleImage.png", "image/png", "image content".getBytes());
        MockMultipartFile processImageFile = new MockMultipartFile("processImage", "processImage.png", "image/png", "image content".getBytes());

        // Act & Assert
        mockMvc.perform(multipart("/api/tutorials/1")
                        .file(tutorialFile)
                        .file(roleImageFile)
                        .file(processImageFile)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()));
    }

    @Test
    public void testDeleteTutorial() throws Exception {
        // Arrange
        doNothing().when(tutorialService).deleteTutorial("1");

        // Act & Assert
        mockMvc.perform(delete("/api/tutorials/1"))
                .andExpect(status().isNoContent());
    }
}
