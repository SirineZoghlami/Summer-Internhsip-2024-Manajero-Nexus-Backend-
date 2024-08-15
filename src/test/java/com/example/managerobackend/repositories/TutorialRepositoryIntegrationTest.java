package com.example.managerobackend.repositories;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
public class TutorialRepositoryIntegrationTest {

    @Autowired
    private TutorialRepository tutorialRepository;

    @BeforeEach
    void setUp() {
        tutorialRepository.deleteAll();  // Clean up database before each test
    }

    @Test
    void whenSaveTutorial_thenFindById() {
        // Given
        Tutorial tutorial = new Tutorial(null, "Introduction", "Why Use", "What is Nexus",
                "How it Works", "Limitations", "Applying Nexus", "Conclusion", "imageUrl");

        // When
        tutorialRepository.save(tutorial);

        // Then
        Tutorial foundTutorial = tutorialRepository.findById(tutorial.getId()).orElse(null);
        assertThat(foundTutorial).isNotNull();
        assertThat(foundTutorial.getIntroduction()).isEqualTo("Introduction");
    }
}
