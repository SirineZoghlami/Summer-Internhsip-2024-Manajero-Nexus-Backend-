package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        Tutorial tutorial = new Tutorial();
        tutorial.setId("1");
        when(tutorialService.saveTutorial(any(Tutorial.class))).thenReturn(tutorial);

        // Act & Assert
        mockMvc.perform(post("/api/tutorials/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"introduction\": \"Intro\" }")) // Update with appropriate fields
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void testGetAllTutorials() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial();
        when(tutorialService.getAllTutorials()).thenReturn(Collections.singletonList(tutorial));

        // Act & Assert
        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0]").isNotEmpty());
    }

    @Test
    public void testGetTutorialById() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial();
        when(tutorialService.getTutorialById("1")).thenReturn(tutorial);

        // Act & Assert
        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testUpdateTutorial() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial();
        tutorial.setId("1");
        when(tutorialService.updateTutorial(eq("1"), any(Tutorial.class))).thenReturn(tutorial);

        // Act & Assert
        mockMvc.perform(put("/api/tutorials/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": \"1\", \"introduction\": \"Updated Intro\" }")) // Update with appropriate fields
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"));
    }



    @Test
    public void testUploadImage() throws Exception {
        // Arrange
        Tutorial tutorial = new Tutorial();
        when(tutorialService.uploadImage(eq("1"), any(MultipartFile.class))).thenReturn(tutorial);

        // Act & Assert
        mockMvc.perform(multipart("/api/tutorials/1/uploadImage")
                        .file("file", "test image content".getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
