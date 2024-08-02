package com.example.managerobackend.services;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.repositories.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public Tutorial saveTutorial(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public Tutorial getTutorialById(String id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    public Tutorial updateTutorial(String id, Tutorial updatedTutorial) {
        return tutorialRepository.findById(id)
                .map(tutorial -> {
                    tutorial.setIntroduction(updatedTutorial.getIntroduction());
                    tutorial.setWhyUse(updatedTutorial.getWhyUse());
                    tutorial.setWhatIsNexus(updatedTutorial.getWhatIsNexus());
                    tutorial.setHowDoesItWork(updatedTutorial.getHowDoesItWork());
                    tutorial.setLimitations(updatedTutorial.getLimitations());
                    tutorial.setApplyingNexus(updatedTutorial.getApplyingNexus());
                    tutorial.setConclusion(updatedTutorial.getConclusion());
                    return tutorialRepository.save(tutorial);
                }).orElseGet(() -> {
                    updatedTutorial.setId(id);
                    return tutorialRepository.save(updatedTutorial);
                });
    }

    public void deleteTutorial(String id) {
        tutorialRepository.deleteById(id);
    }

    public Tutorial uploadImage(String id, MultipartFile file) throws IOException {
        Tutorial tutorial = tutorialRepository.findById(id).orElseThrow(() -> new RuntimeException("Tutorial not found with id: " + id));

        // Save the image to a storage service (or locally) and get the URL
        String imageUrl = saveImage(file);

        // Set the image URL in the tutorial entity
        tutorial.setImageUrl(imageUrl);

        // Save updated tutorial with image URL
        return tutorialRepository.save(tutorial);
    }

    private String saveImage(MultipartFile file) throws IOException {
        // Define the directory where you want to store the images
        String uploadDir = "./uploads/";

        // Create directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file to the upload directory
        Path filePath = uploadPath.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath);

        // Return the relative path (you might want to store the full path or URL)
        return "/uploads/" + file.getOriginalFilename();
    }
}