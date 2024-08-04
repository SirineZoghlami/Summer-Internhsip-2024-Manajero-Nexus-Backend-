package com.example.managerobackend.models;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "nexus_goals")
public class NexusGoal {
    @Id
    private String id;
    private String goal;
}
