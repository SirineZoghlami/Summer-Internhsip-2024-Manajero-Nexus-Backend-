package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Tutorial;
import com.example.managerobackend.services.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tutorials")
public class TutorialController {

    @Autowired
    private TutorialService tutorialService;

    @PostMapping("/create")
    public Tutorial saveTutorial(@RequestBody Tutorial tutorial) {
        return tutorialService.saveTutorial(tutorial);
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
    public Tutorial updateTutorial(@PathVariable String id, @RequestBody Tutorial tutorial) {
        return tutorialService.updateTutorial(id, tutorial);
    }

    @DeleteMapping("/{id}")
    public void deleteTutorial(@PathVariable String id) {
        tutorialService.deleteTutorial(id);
    }

    @PostMapping("/{id}/uploadImage")
    public Tutorial uploadImage(@PathVariable String id, @RequestParam("file") MultipartFile file) throws IOException {
        return tutorialService.uploadImage(id, file);
    }
}
