package com.example.managerobackend.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "product_backlog")
public class ProductBacklogItem {
    @Id
    private String id;
    private String title;
    private String description;
    private String priority;
    private String status;
}