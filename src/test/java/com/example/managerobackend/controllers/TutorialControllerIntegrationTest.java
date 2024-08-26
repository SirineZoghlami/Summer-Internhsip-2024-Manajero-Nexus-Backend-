package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TutorialController.class)
public class TutorialControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorialService tutorialService;

    private Tutorial tutorial;

    @BeforeEach
    public void setup() {
        tutorial = new Tutorial(
                "1",
                "Introduction to Nexus",
                "Why use Nexus",
                "What is Nexus?",
                "How does it work?",
                "Limitations of Nexus",
                "Applying Nexus",
                "Conclusion on Nexus",
                "role-image-url",
                "process-image-url"
        );
    }

    @Test
    public void testSaveTutorial() throws Exception {
        Mockito.when(tutorialService.saveTutorial(any(Tutorial.class), any(), any()))
                .thenReturn(tutorial);

        MockMultipartFile tutorialFile = new MockMultipartFile("tutorial", "", "application/json",
                ("{" +
                        "\"introduction\": \"Introduction to Nexus\"," +
                        "\"whyUse\": \"Why use Nexus\"," +
                        "\"whatIsNexus\": \"What is Nexus?\"," +
                        "\"howDoesItWork\": \"How does it work?\"," +
                        "\"limitations\": \"Limitations of Nexus\"," +
                        "\"applyingNexus\": \"Applying Nexus\"," +
                        "\"conclusion\": \"Conclusion on Nexus\"" +
                        "}").getBytes());

        mockMvc.perform(multipart("/api/tutorials/create")
                        .file(tutorialFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.introduction").value("Introduction to Nexus"))
                .andExpect(jsonPath("$.whyUse").value("Why use Nexus"))
                .andExpect(jsonPath("$.whatIsNexus").value("What is Nexus?"))
                .andExpect(jsonPath("$.howDoesItWork").value("How does it work?"))
                .andExpect(jsonPath("$.limitations").value("Limitations of Nexus"))
                .andExpect(jsonPath("$.applyingNexus").value("Applying Nexus"))
                .andExpect(jsonPath("$.conclusion").value("Conclusion on Nexus"))
                .andExpect(jsonPath("$.roleImageUrl").value("role-image-url"))
                .andExpect(jsonPath("$.processImageUrl").value("process-image-url"));
    }

    @Test
    public void testGetAllTutorials() throws Exception {
        List<Tutorial> tutorials = Arrays.asList(tutorial);
        Mockito.when(tutorialService.getAllTutorials())
                .thenReturn(tutorials);

        mockMvc.perform(get("/api/tutorials"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].introduction").value("Introduction to Nexus"))
                .andExpect(jsonPath("$[0].whyUse").value("Why use Nexus"))
                .andExpect(jsonPath("$[0].whatIsNexus").value("What is Nexus?"))
                .andExpect(jsonPath("$[0].howDoesItWork").value("How does it work?"))
                .andExpect(jsonPath("$[0].limitations").value("Limitations of Nexus"))
                .andExpect(jsonPath("$[0].applyingNexus").value("Applying Nexus"))
                .andExpect(jsonPath("$[0].conclusion").value("Conclusion on Nexus"))
                .andExpect(jsonPath("$[0].roleImageUrl").value("role-image-url"))
                .andExpect(jsonPath("$[0].processImageUrl").value("process-image-url"));
    }

    @Test
    public void testGetTutorialById() throws Exception {
        Mockito.when(tutorialService.getTutorialById("1"))
                .thenReturn(tutorial);

        mockMvc.perform(get("/api/tutorials/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.introduction").value("Introduction to Nexus"))
                .andExpect(jsonPath("$.whyUse").value("Why use Nexus"))
                .andExpect(jsonPath("$.whatIsNexus").value("What is Nexus?"))
                .andExpect(jsonPath("$.howDoesItWork").value("How does it work?"))
                .andExpect(jsonPath("$.limitations").value("Limitations of Nexus"))
                .andExpect(jsonPath("$.applyingNexus").value("Applying Nexus"))
                .andExpect(jsonPath("$.conclusion").value("Conclusion on Nexus"))
                .andExpect(jsonPath("$.roleImageUrl").value("role-image-url"))
                .andExpect(jsonPath("$.processImageUrl").value("process-image-url"));
    }

    @Test
    public void testUpdateTutorial() throws Exception {
        Mockito.when(tutorialService.updateTutorial(eq("1"), any(Tutorial.class), any(), any()))
                .thenReturn(tutorial);

        MockMultipartFile tutorialFile = new MockMultipartFile("tutorial", "", "application/json",
                ("{" +
                        "\"introduction\": \"Updated Introduction\"," +
                        "\"whyUse\": \"Updated Why use Nexus\"," +
                        "\"whatIsNexus\": \"Updated What is Nexus?\"," +
                        "\"howDoesItWork\": \"Updated How does it work?\"," +
                        "\"limitations\": \"Updated Limitations of Nexus\"," +
                        "\"applyingNexus\": \"Updated Applying Nexus\"," +
                        "\"conclusion\": \"Updated Conclusion on Nexus\"" +
                        "}").getBytes());

        mockMvc.perform(multipart("/api/tutorials/1")
                        .file(tutorialFile)
                        .with(request -> { request.setMethod("PUT"); return request; })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.introduction").value("Introduction to Nexus"))
                .andExpect(jsonPath("$.whyUse").value("Why use Nexus"))
                .andExpect(jsonPath("$.whatIsNexus").value("What is Nexus?"))
                .andExpect(jsonPath("$.howDoesItWork").value("How does it work?"))
                .andExpect(jsonPath("$.limitations").value("Limitations of Nexus"))
                .andExpect(jsonPath("$.applyingNexus").value("Applying Nexus"))
                .andExpect(jsonPath("$.conclusion").value("Conclusion on Nexus"))
                .andExpect(jsonPath("$.roleImageUrl").value("role-image-url"))
                .andExpect(jsonPath("$.processImageUrl").value("process-image-url"));
    }

    @Test
    public void testDeleteTutorial() throws Exception {
        Mockito.doNothing().when(tutorialService).deleteTutorial("1");

        mockMvc.perform(delete("/api/tutorials/1"))
                .andExpect(status().isNoContent());
    }
}
