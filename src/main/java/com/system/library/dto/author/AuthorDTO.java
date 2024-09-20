package com.system.library.dto.author;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.library.model.Author;
import com.system.library.model.Book;
import com.system.library.model.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO {

    private Long id;

    private String name;

    private String biography;

    private LocalDate dob;

    public AuthorDTO(Long id, String name, String biography, LocalDate dob) {
        this.id = id;
        this.name = name;
        this.biography = biography;
        this.dob = dob;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
