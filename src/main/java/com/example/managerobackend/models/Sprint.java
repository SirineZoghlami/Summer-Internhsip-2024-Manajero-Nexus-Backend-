package com.example.managerobackend.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "sprints")
public class Sprint {
    @Id
    private String id;
    private int sprintNumber;
    private Date sprintStartDate;
    private Date sprintEndDate;
    private List<SprintBacklogItem> sprintBacklog;
    private List<Review> reviews;
}