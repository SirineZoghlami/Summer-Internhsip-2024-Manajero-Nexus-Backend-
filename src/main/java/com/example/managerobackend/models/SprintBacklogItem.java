package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sprint_backlog")
public class SprintBacklogItem {
    @Id
    private String id;
    private String title;
    private String description;
    private String priority;
    private String status;
}