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
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    private final String uploadDir = "./uploads/";

    public Tutorial saveTutorial(Tutorial tutorial, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String imageUrl = saveImage(file);
            tutorial.setImageUrl(imageUrl);
        }
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public Tutorial getTutorialById(String id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    public Tutorial updateTutorial(String id, Tutorial updatedTutorial, MultipartFile file) throws IOException {
        return tutorialRepository.findById(id)
                .map(tutorial -> {
                    tutorial.setIntroduction(updatedTutorial.getIntroduction());
                    tutorial.setWhyUse(updatedTutorial.getWhyUse());
                    tutorial.setWhatIsNexus(updatedTutorial.getWhatIsNexus());
                    tutorial.setHowDoesItWork(updatedTutorial.getHowDoesItWork());
                    tutorial.setLimitations(updatedTutorial.getLimitations());
                    tutorial.setApplyingNexus(updatedTutorial.getApplyingNexus());
                    tutorial.setConclusion(updatedTutorial.getConclusion());
                    if (file != null && !file.isEmpty()) {
                        try {
                            String imageUrl = saveImage(file);
                            tutorial.setImageUrl(imageUrl);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save image", e);
                        }
                    }
                    return tutorialRepository.save(tutorial);
                }).orElseGet(() -> {
                    updatedTutorial.setId(id);
                    if (file != null && !file.isEmpty()) {
                        try {
                            String imageUrl = saveImage(file);
                            updatedTutorial.setImageUrl(imageUrl);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save image", e);
                        }
                    }
                    return tutorialRepository.save(updatedTutorial);
                });
    }

    public void deleteTutorial(String id) {
        tutorialRepository.deleteById(id);
    }

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Validate file type (example: only allow JPEG and PNG)
        String fileType = Files.probeContentType(filePath);
        if (!fileType.equals("image/jpeg") && !fileType.equals("image/png")) {
            throw new IOException("Unsupported file type: " + fileType);
        }

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + uniqueFilename;
    }
}
