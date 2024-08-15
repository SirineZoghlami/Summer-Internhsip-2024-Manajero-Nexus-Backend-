package com.example.managerobackend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tutorials")
public class Tutorial {
    @Id
    private String id;
    private String introduction;
    private String whyUse;
    private String whatIsNexus;
    private String howDoesItWork;
    private String limitations;
    private String applyingNexus;
    private String conclusion;
    private String imageUrl; // New field for image URL
}
