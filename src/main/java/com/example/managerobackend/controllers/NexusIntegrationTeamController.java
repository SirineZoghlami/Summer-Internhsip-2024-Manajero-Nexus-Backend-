package com.example.managerobackend.controllers;

import com.example.managerobackend.models.NexusIntegrationTeam;
import com.example.managerobackend.services.NexusIntegrationTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nexus-integration-teams")
public class NexusIntegrationTeamController {

    @Autowired
    private NexusIntegrationTeamService nexusIntegrationTeamService;

    @GetMapping
    public ResponseEntity<List<NexusIntegrationTeam>> getAllTeams() {
        List<NexusIntegrationTeam> teams = nexusIntegrationTeamService.getAllNexusIntegrationTeams();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NexusIntegrationTeam> getTeamById(@PathVariable("id") String id) {
        Optional<NexusIntegrationTeam> team = nexusIntegrationTeamService.getNexusIntegrationTeamById(id);
        return team.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NexusIntegrationTeam> createTeam(@RequestBody NexusIntegrationTeam team) {
        NexusIntegrationTeam createdTeam = nexusIntegrationTeamService.createNexusIntegrationTeam(team);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NexusIntegrationTeam> updateTeam(@PathVariable("id") String id, @RequestBody NexusIntegrationTeam team) {
        try {
            NexusIntegrationTeam updatedTeam = nexusIntegrationTeamService.updateNexusIntegrationTeam(id, team);
            return new ResponseEntity<>(updatedTeam, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable("id") String id) {
        nexusIntegrationTeamService.deleteNexusIntegrationTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
