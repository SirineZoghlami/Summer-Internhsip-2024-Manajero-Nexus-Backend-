package com.example.managerobackend.models;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getWhyUse() {
        return whyUse;
    }

    public void setWhyUse(String whyUse) {
        this.whyUse = whyUse;
    }

    public String getWhatIsNexus() {
        return whatIsNexus;
    }

    public void setWhatIsNexus(String whatIsNexus) {
        this.whatIsNexus = whatIsNexus;
    }

    public String getHowDoesItWork() {
        return howDoesItWork;
    }

    public void setHowDoesItWork(String howDoesItWork) {
        this.howDoesItWork = howDoesItWork;
    }

    public String getLimitations() {
        return limitations;
    }

    public void setLimitations(String limitations) {
        this.limitations = limitations;
    }

    public String getApplyingNexus() {
        return applyingNexus;
    }

    public void setApplyingNexus(String applyingNexus) {
        this.applyingNexus = applyingNexus;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}