package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Tutorial saveTutorial(@RequestPart("tutorial") Tutorial tutorial,
                                 @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return tutorialService.saveTutorial(tutorial, file);
    }

    @GetMapping
    public List<Tutorial> getAllTutorials() {
        return tutorialService.getAllTutorials();
    }

    @GetMapping("/{id}")
    public Tutorial getTutorialById(@PathVariable String id) {
        return tutorialService.getTutorialById(id);
    }

    @PutMapping("/{id}")
    public Tutorial updateTutorial(@PathVariable String id,
                                   @RequestPart("tutorial") Tutorial tutorial,
                                   @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        return tutorialService.updateTutorial(id, tutorial, file);
    }

    @DeleteMapping("/{id}")
    public void deleteTutorial(@PathVariable String id) {
        tutorialService.deleteTutorial(id);
    }
}