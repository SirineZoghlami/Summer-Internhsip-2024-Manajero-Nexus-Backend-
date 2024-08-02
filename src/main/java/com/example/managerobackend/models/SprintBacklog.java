package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "sprintbacklog")
public class SprintBacklog {
    @Id
    private String id;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private String status;
}
