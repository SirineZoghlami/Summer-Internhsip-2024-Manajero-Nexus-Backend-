package com.example.managerobackend.services;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class TutorialServiceIntegrationTest {

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private TutorialRepository tutorialRepository;

    private Tutorial testTutorial;

    @BeforeEach
    public void setUp() {
        // Clean up the collection before each test
        tutorialRepository.deleteAll();

        // Set up a test tutorial
        testTutorial = new Tutorial();
        testTutorial.setIntroduction("Introduction");
        testTutorial.setWhyUse("Why use it");
        testTutorial.setWhatIsNexus("What is Nexus");
        testTutorial.setHowDoesItWork("How does it work");
        testTutorial.setLimitations("Limitations");
        testTutorial.setApplyingNexus("Applying Nexus");
        testTutorial.setConclusion("Conclusion");
        // Add other fields if necessary
    }

    @Test
    public void testSaveAndFindById() {
        // Save the test tutorial
        Tutorial savedTutorial = tutorialService.saveTutorial(testTutorial);

        // Find by ID
        Tutorial foundTutorial = tutorialService.getTutorialById(savedTutorial.getId());

        // Verify the saved tutorial is the same as the found tutorial
        assertNotNull(foundTutorial);
        assertEquals(savedTutorial.getId(), foundTutorial.getId());
        assertEquals(savedTutorial.getIntroduction(), foundTutorial.getIntroduction());
    }

    @Test
    public void testUpdateTutorial() {
        // Save the test tutorial
        Tutorial savedTutorial = tutorialService.saveTutorial(testTutorial);

        // Update tutorial
        savedTutorial.setIntroduction("Updated Introduction");
        Tutorial updatedTutorial = tutorialService.updateTutorial(savedTutorial.getId(), savedTutorial);

        // Verify the updated tutorial
        assertEquals("Updated Introduction", updatedTutorial.getIntroduction());
    }

    @Test
    public void testDeleteTutorial() {
        // Save the test tutorial
        Tutorial savedTutorial = tutorialService.saveTutorial(testTutorial);

        // Delete the tutorial
        tutorialService.deleteTutorial(savedTutorial.getId());

        // Verify the tutorial is deleted
        Tutorial deletedTutorial = tutorialService.getTutorialById(savedTutorial.getId());
        assertNull(deletedTutorial);
    }

    @Test
    public void testUploadImage() throws IOException {
        // Save the test tutorial
        Tutorial savedTutorial = tutorialService.saveTutorial(testTutorial);

        // Create a mock MultipartFile
        MultipartFile file = new MockMultipartFile("file", "test-image.png", "image/png", "test image content".getBytes());

        // Upload the image
        Tutorial updatedTutorial = tutorialService.uploadImage(savedTutorial.getId(), file);

        // Verify that the image URL is set
        assertTrue(updatedTutorial.getImageUrl().contains("/uploads/"));
    }
}
