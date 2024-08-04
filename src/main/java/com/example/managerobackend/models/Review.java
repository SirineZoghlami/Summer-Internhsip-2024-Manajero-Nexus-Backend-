package com.example.managerobackend.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private Date reviewDate;
    private String feedback;
}