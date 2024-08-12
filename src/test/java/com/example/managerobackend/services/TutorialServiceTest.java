package com.example.managerobackend.services;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TutorialServiceTest {

    @InjectMocks
    private TutorialService tutorialService;

    @Mock
    private TutorialRepository tutorialRepository;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTutorial() {
        // Arrange
        Tutorial tutorial = new Tutorial();
        tutorial.setId("1");
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(tutorial);

        // Act
        Tutorial result = tutorialService.saveTutorial(tutorial);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    public void testGetAllTutorials() {
        // Arrange
        Tutorial tutorial = new Tutorial();
        tutorial.setId("1");
        when(tutorialRepository.findAll()).thenReturn(Arrays.asList(tutorial));

        // Act
        List<Tutorial> result = tutorialService.getAllTutorials();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    public void testGetTutorialById() {
        // Arrange
        Tutorial tutorial = new Tutorial();
        tutorial.setId("1");
        when(tutorialRepository.findById(anyString())).thenReturn(Optional.of(tutorial));

        // Act
        Tutorial result = tutorialService.getTutorialById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
    }

    @Test
    public void testUpdateTutorial() {
        // Arrange
        Tutorial existingTutorial = new Tutorial();
        existingTutorial.setId("1");
        existingTutorial.setIntroduction("Old Introduction");

        Tutorial updatedTutorial = new Tutorial();
        updatedTutorial.setIntroduction("New Introduction");

        when(tutorialRepository.findById(anyString())).thenReturn(Optional.of(existingTutorial));
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(existingTutorial);

        // Act
        Tutorial result = tutorialService.updateTutorial("1", updatedTutorial);

        // Assert
        assertNotNull(result);
        assertEquals("New Introduction", result.getIntroduction());
        verify(tutorialRepository).save(existingTutorial);
    }

    @Test
    public void testUpdateTutorialNotFound() {
        // Arrange
        Tutorial updatedTutorial = new Tutorial();
        updatedTutorial.setId("1");

        when(tutorialRepository.findById(anyString())).thenReturn(Optional.empty());
        when(tutorialRepository.save(any(Tutorial.class))).thenReturn(updatedTutorial);

        // Act
        Tutorial result = tutorialService.updateTutorial("1", updatedTutorial);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(tutorialRepository).save(updatedTutorial);
    }

    @Test
    public void testDeleteTutorial() {
        // Act
        tutorialService.deleteTutorial("1");

        // Assert
        verify(tutorialRepository).deleteById("1");
    }


    // Helper method to create a dummy image file for testing
    private Path createTempFile(String filename) throws IOException {
        Path tempFile = Files.createTempFile(filename, ".png");
        Files.write(tempFile, new byte[10]); // Create a dummy file with 10 bytes
        return tempFile;
    }
}
