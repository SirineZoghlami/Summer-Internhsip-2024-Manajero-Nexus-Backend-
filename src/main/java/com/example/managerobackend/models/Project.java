package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "projects")
public class Project {
    @Id
    private String id;
    private String projectName;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<NexusIntegrationTeam> nexusIntegrationTeam;
    private String nexusGoalId; // Reference to the NexusGoal
    private List<ProductBacklogItem> productBacklog;
    private List<Sprint> sprints;
}
