package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "nexus_integration_teams")
public class NexusIntegrationTeam {
    @Id
    private String id;
    private String teamName;
    private String roles;
    private String members;

}