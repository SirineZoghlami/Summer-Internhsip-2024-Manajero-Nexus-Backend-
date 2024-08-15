package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(TutorialController.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TutorialControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TutorialService tutorialService;

    private Tutorial testTutorial;

    @BeforeEach
    public void setUp() {
        testTutorial = new Tutorial();
        testTutorial.setId("123");
        testTutorial.setIntroduction("Introduction");
        testTutorial.setWhyUse("Why use");
        testTutorial.setWhatIsNexus("What is Nexus");
        testTutorial.setHowDoesItWork("How does it work");
        testTutorial.setLimitations("Limitations");
        testTutorial.setApplyingNexus("Applying Nexus");
        testTutorial.setConclusion("Conclusion");
    }

    @Test
    public void testSaveTutorial() throws Exception {
        when(tutorialService.saveTutorial(any(Tutorial.class))).thenReturn(testTutorial);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/tutorials/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"introduction\":\"Introduction\", \"introductionImageUrl\":\"/images/intro.png\", \"whyUse\":\"Why use\", \"whatIsNexus\":\"What is Nexus\", \"whatIsNexusImageUrl\":\"/images/what.png\", \"howDoesItWork\":\"How does it work\", \"limitations\":\"Limitations\", \"applyingNexus\":\"Applying Nexus\", \"conclusion\":\"Conclusion\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.introduction").value("Introduction"));
    }

    @Test
    public void testGetAllTutorials() throws Exception {
        when(tutorialService.getAllTutorials()).thenReturn(Collections.singletonList(testTutorial));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].introduction").value("Introduction"));
    }

    @Test
    public void testGetTutorialById() throws Exception {
        when(tutorialService.getTutorialById("123")).thenReturn(testTutorial);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/tutorials/123")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.introduction").value("Introduction"));
    }




}
