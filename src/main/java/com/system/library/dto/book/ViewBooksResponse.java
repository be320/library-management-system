package com.system.library.dto.book;

import jakarta.validation.constraints.NotNull;

import java.util.List;


public class ViewBooksResponse {

    @NotNull
    private List<BookDTO> books;

    public ViewBooksResponse(List<BookDTO> books) {
        this.books = books;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
