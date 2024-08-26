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

    public Tutorial saveTutorial(Tutorial tutorial, MultipartFile roleImage, MultipartFile processImage) throws IOException {
        if (roleImage != null && !roleImage.isEmpty()) {
            String roleImageUrl = saveImage(roleImage);
            tutorial.setRoleImageUrl(roleImageUrl);
        }
        if (processImage != null && !processImage.isEmpty()) {
            String processImageUrl = saveImage(processImage);
            tutorial.setProcessImageUrl(processImageUrl);
        }
        return tutorialRepository.save(tutorial);
    }

    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    public Tutorial getTutorialById(String id) {
        return tutorialRepository.findById(id).orElse(null);
    }

    public Tutorial updateTutorial(String id, Tutorial updatedTutorial, MultipartFile roleImage, MultipartFile processImage) throws IOException {
        return tutorialRepository.findById(id)
                .map(tutorial -> {
                    // Update all fields from updatedTutorial
                    tutorial.setIntroduction(updatedTutorial.getIntroduction());
                    tutorial.setWhyUse(updatedTutorial.getWhyUse());
                    tutorial.setWhatIsNexus(updatedTutorial.getWhatIsNexus());
                    tutorial.setHowDoesItWork(updatedTutorial.getHowDoesItWork());
                    tutorial.setLimitations(updatedTutorial.getLimitations());
                    tutorial.setApplyingNexus(updatedTutorial.getApplyingNexus());
                    tutorial.setConclusion(updatedTutorial.getConclusion());

                    // Handle roleImage if provided
                    if (roleImage != null && !roleImage.isEmpty()) {
                        try {
                            String roleImageUrl = saveImage(roleImage);
                            tutorial.setRoleImageUrl(roleImageUrl);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save role image", e);
                        }
                    }

                    // Handle processImage if provided
                    if (processImage != null && !processImage.isEmpty()) {
                        try {
                            String processImageUrl = saveImage(processImage);
                            tutorial.setProcessImageUrl(processImageUrl);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to save process image", e);
                        }
                    }

                    return tutorialRepository.save(tutorial);
                })
                .orElseGet(() -> {
                    // If the tutorial does not exist, create a new one
                    updatedTutorial.setId(id);

                    if (roleImage != null && !roleImage.isEmpty()) {
                        String roleImageUrl = null;
                        try {
                            roleImageUrl = saveImage(roleImage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        updatedTutorial.setRoleImageUrl(roleImageUrl);
                    }

                    if (processImage != null && !processImage.isEmpty()) {
                        String processImageUrl = null;
                        try {
                            processImageUrl = saveImage(processImage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        updatedTutorial.setProcessImageUrl(processImageUrl);
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
        if (fileType == null || (!fileType.equals("image/jpeg") && !fileType.equals("image/png"))) {
            throw new IOException("Unsupported file type: " + fileType);
        }

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/uploads/" + uniqueFilename;
    }
}
