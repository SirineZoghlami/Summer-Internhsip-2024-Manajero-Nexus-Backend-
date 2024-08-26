package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tutorials")
public class TutorialController {

    private final TutorialService tutorialService;

    @Autowired
    public TutorialController(TutorialService tutorialService) {
        this.tutorialService = tutorialService;
    }

    @PostMapping("/create")
    public ResponseEntity<Tutorial> saveTutorial(
            @RequestPart("tutorial") Tutorial tutorial,
            @RequestPart(value = "roleImage", required = false) MultipartFile roleImage,
            @RequestPart(value = "processImage", required = false) MultipartFile processImage) throws IOException {
        Tutorial savedTutorial = tutorialService.saveTutorial(tutorial, roleImage, processImage);
        return ResponseEntity.ok(savedTutorial);
    }

    @GetMapping
    public ResponseEntity<List<Tutorial>> getAllTutorials() {
        List<Tutorial> tutorials = tutorialService.getAllTutorials();
        return ResponseEntity.ok(tutorials);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutorial> getTutorialById(@PathVariable String id) {
        Tutorial tutorial = tutorialService.getTutorialById(id);
        return tutorial != null ? ResponseEntity.ok(tutorial) : ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Tutorial> updateTutorial(
            @PathVariable String id,
            @RequestPart("tutorial") Tutorial tutorial,
            @RequestPart(value = "roleImage", required = false) MultipartFile roleImage,
            @RequestPart(value = "processImage", required = false) MultipartFile processImage) throws IOException {
        Tutorial updatedTutorial = tutorialService.updateTutorial(id, tutorial, roleImage, processImage);
        return ResponseEntity.ok(updatedTutorial);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutorial(@PathVariable String id) {
        tutorialService.deleteTutorial(id);
        return ResponseEntity.noContent().build();
    }
}