package com.system.library.dto.author;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public class ViewAuthorsResponse {

    @NotNull
    private List<AuthorDTO> authors;

    public ViewAuthorsResponse(List<AuthorDTO> authors) {
        this.authors = authors;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }
}
