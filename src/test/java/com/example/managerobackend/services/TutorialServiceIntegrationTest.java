package com.example.managerobackend.services;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")  // Use application-test.properties configuration
public class TutorialServiceIntegrationTest {

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private TutorialRepository tutorialRepository;

    private Tutorial tutorial1;
    private Tutorial tutorial2;

    @BeforeEach
    public void setup() {
        tutorialRepository.deleteAll();

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
    }



    @Test
    public void testGetAllTutorials() {
        tutorialRepository.save(tutorial1);
        tutorialRepository.save(tutorial2);

        List<Tutorial> tutorials = tutorialService.getAllTutorials();
        assertThat(tutorials).hasSize(2);
        assertThat(tutorials).contains(tutorial1, tutorial2);
    }

    @Test
    public void testGetTutorialById() {
        tutorialRepository.save(tutorial1);

        Tutorial foundTutorial = tutorialService.getTutorialById("1");
        assertThat(foundTutorial).isNotNull();
        assertThat(foundTutorial.getIntroduction()).isEqualTo("Introduction to Nexus");
    }



    @Test
    public void testDeleteTutorial() {
        tutorialRepository.save(tutorial1);
        tutorialService.deleteTutorial("1");

        Tutorial deletedTutorial = tutorialService.getTutorialById("1");
        assertThat(deletedTutorial).isNull();
    }
}
