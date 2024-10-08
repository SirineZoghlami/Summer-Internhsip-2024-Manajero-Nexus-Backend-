package com.example.managerobackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductBacklogItem {

        private String id;
        private String title;
        private String description;
        private String priority;
        private String status;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sprint {
        private int number;
        private Date startDate;
        private Date endDate;

        private List<Review> reviews;
        private boolean completed;


    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {
        private LocalDateTime reviewDate;
        private String reviewContent;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Team {
        private String name; // Name of the team
        private String description; // Optional description of the team
        private List<TeamMember> members; // List of team members
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TeamMember {
        private String name; // Name of the team member
        private String role; // Role of the team member within the team (e.g., Developer, Scrum Master)
    }
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NexusGoal {
        private String content;
    }
}