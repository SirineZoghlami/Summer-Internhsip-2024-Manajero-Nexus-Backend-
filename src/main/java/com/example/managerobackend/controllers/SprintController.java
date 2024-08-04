package com.example.managerobackend.controllers;

import com.example.managerobackend.models.Sprint;
import com.example.managerobackend.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {

    @Autowired
    private SprintService sprintService;

    @GetMapping
    public ResponseEntity<List<Sprint>> getAllSprints() {
        List<Sprint> sprints = sprintService.getAllSprints();
        return new ResponseEntity<>(sprints, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sprint> getSprintById(@PathVariable("id") String id) {
        Optional<Sprint> sprint = sprintService.getSprintById(id);
        return sprint.map(item -> new ResponseEntity<>(item, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Sprint> createSprint(@RequestBody Sprint sprint) {
        Sprint createdSprint = sprintService.createOrUpdateSprint(sprint);
        return new ResponseEntity<>(createdSprint, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sprint> updateSprint(@PathVariable("id") String id,
                                               @RequestBody Sprint sprint) {
        sprint.setId(id);
        Sprint updatedSprint = sprintService.createOrUpdateSprint(sprint);
        return new ResponseEntity<>(updatedSprint, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSprint(@PathVariable("id") String id) {
        sprintService.deleteSprint(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
