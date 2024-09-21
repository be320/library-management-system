package com.system.library.dto.author;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class SaveAuthorRequest {

    private String name;

    private String biography;

    private LocalDate dob;

    public SaveAuthorRequest(String name, String biography, LocalDate dob) {
        this.name = name;
        this.biography = biography;
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
}
