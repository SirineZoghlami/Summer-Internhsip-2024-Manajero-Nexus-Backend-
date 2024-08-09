package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "nexus_projects")
public class NexusProject {
    @Id
    private String id;
    private String projectName;
    private String description;
    private Date startDate;
    private Date endDate;
    private List<ProductBacklogItem> productBacklog;
    private List<Sprint> sprints;
    private List<Team> teams;
    private List<NexusGoal> goals;

    @Data
    public static class ProductBacklogItem {
        private String title;
        private String description;
        private String priority;
        private String status;
    }

    @Data
    public static class Sprint {
        private int number;
        private Date startDate;
        private Date endDate;
        private String reviews;
    }

    @Data
    public static class Team {
        private String name;
        private List<String> roles;
        private List<String> members;
    }

    @Data
    public static class NexusGoal {
        private String content;
    }
}
