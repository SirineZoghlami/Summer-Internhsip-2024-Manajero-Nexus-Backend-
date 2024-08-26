package com.example.managerobackend.services;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TutorialServiceTest {

    @Mock
    private TutorialRepository tutorialRepository;

    @InjectMocks
    private TutorialService tutorialService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveTutorial_withImages() throws IOException {
        // Arrange
        Tutorial tutorial = new Tutorial();
        MockMultipartFile roleImage = new MockMultipartFile("roleImage", "roleImage.png", "image/png", "Role Image Content".getBytes());
        MockMultipartFile processImage = new MockMultipartFile("processImage", "processImage.png", "image/png", "Process Image Content".getBytes());

        when(tutorialRepository.save(any(Tutorial.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Tutorial savedTutorial = tutorialService.saveTutorial(tutorial, roleImage, processImage);

        // Assert
        assertNotNull(savedTutorial.getRoleImageUrl());
        assertTrue(savedTutorial.getRoleImageUrl().startsWith("/uploads/"));
        assertNotNull(savedTutorial.getProcessImageUrl());
        assertTrue(savedTutorial.getProcessImageUrl().startsWith("/uploads/"));
        verify(tutorialRepository, times(1)).save(any(Tutorial.class));
    }

    @Test
    public void testSaveTutorial_withoutImages() throws IOException {
        // Arrange
        Tutorial tutorial = new Tutorial();

        when(tutorialRepository.save(any(Tutorial.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Tutorial savedTutorial = tutorialService.saveTutorial(tutorial, null, null);

        // Assert
        assertNull(savedTutorial.getRoleImageUrl());
        assertNull(savedTutorial.getProcessImageUrl());
        verify(tutorialRepository, times(1)).save(any(Tutorial.class));
    }

    @Test
    public void testGetAllTutorials() {
        // Arrange
        Tutorial tutorial1 = new Tutorial();
        Tutorial tutorial2 = new Tutorial();
        when(tutorialRepository.findAll()).thenReturn(Arrays.asList(tutorial1, tutorial2));

        // Act
        List<Tutorial> tutorials = tutorialService.getAllTutorials();

        // Assert
        assertEquals(2, tutorials.size());
        verify(tutorialRepository, times(1)).findAll();
    }

    @Test
    public void testGetTutorialById() {
        // Arrange
        String id = UUID.randomUUID().toString();
        Tutorial tutorial = new Tutorial();
        when(tutorialRepository.findById(id)).thenReturn(Optional.of(tutorial));

        // Act
        Tutorial foundTutorial = tutorialService.getTutorialById(id);

        // Assert
        assertNotNull(foundTutorial);
        verify(tutorialRepository, times(1)).findById(id);
    }

    @Test
    public void testGetTutorialById_NotFound() {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(tutorialRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Tutorial foundTutorial = tutorialService.getTutorialById(id);

        // Assert
        assertNull(foundTutorial);
        verify(tutorialRepository, times(1)).findById(id);
    }

    @Test
    public void testUpdateTutorial_withImages() throws IOException {
        // Arrange
        String id = UUID.randomUUID().toString();
        Tutorial existingTutorial = new Tutorial();
        Tutorial updatedTutorial = new Tutorial();
        updatedTutorial.setIntroduction("Updated Introduction");
        MockMultipartFile roleImage = new MockMultipartFile("roleImage", "roleImage.png", "image/png", "Role Image Content".getBytes());
        MockMultipartFile processImage = new MockMultipartFile("processImage", "processImage.png", "image/png", "Process Image Content".getBytes());

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(existingTutorial));
        when(tutorialRepository.save(any(Tutorial.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Tutorial savedTutorial = tutorialService.updateTutorial(id, updatedTutorial, roleImage, processImage);

        // Assert
        assertEquals("Updated Introduction", savedTutorial.getIntroduction());
        assertNotNull(savedTutorial.getRoleImageUrl());
        assertTrue(savedTutorial.getRoleImageUrl().startsWith("/uploads/"));
        assertNotNull(savedTutorial.getProcessImageUrl());
        assertTrue(savedTutorial.getProcessImageUrl().startsWith("/uploads/"));
        verify(tutorialRepository, times(1)).findById(id);
        verify(tutorialRepository, times(1)).save(any(Tutorial.class));
    }

    @Test
    public void testUpdateTutorial_withoutImages() throws IOException {
        // Arrange
        String id = UUID.randomUUID().toString();
        Tutorial existingTutorial = new Tutorial();
        Tutorial updatedTutorial = new Tutorial();
        updatedTutorial.setIntroduction("Updated Introduction");

        when(tutorialRepository.findById(id)).thenReturn(Optional.of(existingTutorial));
        when(tutorialRepository.save(any(Tutorial.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Tutorial savedTutorial = tutorialService.updateTutorial(id, updatedTutorial, null, null);

        // Assert
        assertEquals("Updated Introduction", savedTutorial.getIntroduction());
        assertNull(savedTutorial.getRoleImageUrl());
        assertNull(savedTutorial.getProcessImageUrl());
        verify(tutorialRepository, times(1)).findById(id);
        verify(tutorialRepository, times(1)).save(any(Tutorial.class));
    }

    @Test
    public void testDeleteTutorial() {
        // Arrange
        String id = UUID.randomUUID().toString();
        doNothing().when(tutorialRepository).deleteById(id);

        // Act
        tutorialService.deleteTutorial(id);

        // Assert
        verify(tutorialRepository, times(1)).deleteById(id);
    }



}
