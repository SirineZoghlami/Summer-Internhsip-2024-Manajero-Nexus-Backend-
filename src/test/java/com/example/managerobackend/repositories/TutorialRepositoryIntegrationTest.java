package com.example.managerobackend.repositories;

import com.example.managerobackend.models.Tutorial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class TutorialRepositoryIntegrationTest {

    @Autowired
    private TutorialRepository tutorialRepository;

    private Tutorial tutorial1;
    private Tutorial tutorial2;

    @BeforeEach
    public void setup() {
        tutorial1 = new Tutorial(
                "1",
                "Introduction to Nexus",
                "Why use Nexus",
                "What is Nexus?",
                "How does it work?",
                "Limitations of Nexus",
                "Applying Nexus",
                "Conclusion on Nexus",
                "role-image-url-1",
                "process-image-url-1"
        );

        tutorial2 = new Tutorial(
                "2",
                "Advanced Nexus",
                "Why use Advanced Nexus",
                "What is Advanced Nexus?",
                "How does it work in detail?",
                "Advanced Limitations",
                "Advanced Application",
                "Conclusion on Advanced Nexus",
                "role-image-url-2",
                "process-image-url-2"
        );

        tutorialRepository.deleteAll();
    }

    @Test
    public void testSaveTutorial() {
        Tutorial savedTutorial = tutorialRepository.save(tutorial1);
        assertThat(savedTutorial).isNotNull();
        assertThat(savedTutorial.getId()).isEqualTo("1");
    }

    @Test
    public void testFindById() {
        tutorialRepository.save(tutorial1);
        Optional<Tutorial> foundTutorial = tutorialRepository.findById("1");
        assertThat(foundTutorial).isPresent();
        assertThat(foundTutorial.get().getIntroduction()).isEqualTo("Introduction to Nexus");
    }

    @Test
    public void testFindAll() {
        tutorialRepository.save(tutorial1);
        tutorialRepository.save(tutorial2);

        List<Tutorial> tutorials = tutorialRepository.findAll();
        assertThat(tutorials).hasSize(2);
        assertThat(tutorials.get(0).getId()).isEqualTo("1");
        assertThat(tutorials.get(1).getId()).isEqualTo("2");
    }

    @Test
    public void testDeleteById() {
        tutorialRepository.save(tutorial1);
        tutorialRepository.deleteById("1");

        Optional<Tutorial> deletedTutorial = tutorialRepository.findById("1");
        assertThat(deletedTutorial).isNotPresent();
    }

    @Test
    public void testUpdateTutorial() {
        tutorialRepository.save(tutorial1);

        Tutorial updatedTutorial = new Tutorial(
                "1",
                "Updated Introduction",
                "Updated Why use Nexus",
                "Updated What is Nexus?",
                "Updated How does it work?",
                "Updated Limitations",
                "Updated Applying Nexus",
                "Updated Conclusion",
                "updated-role-image-url",
                "updated-process-image-url"
        );

        tutorialRepository.save(updatedTutorial);

        Optional<Tutorial> foundTutorial = tutorialRepository.findById("1");
        assertThat(foundTutorial).isPresent();
        assertThat(foundTutorial.get().getIntroduction()).isEqualTo("Updated Introduction");
    }

    @Test
    public void testDeleteAll() {
        tutorialRepository.save(tutorial1);
        tutorialRepository.save(tutorial2);
        tutorialRepository.deleteAll();

        List<Tutorial> tutorials = tutorialRepository.findAll();
        assertThat(tutorials).isEmpty();
    }
}
