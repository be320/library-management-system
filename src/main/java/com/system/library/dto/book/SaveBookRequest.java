package com.system.library.dto.book;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class SaveBookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotBlank
    private LocalDate publishedDate;

    @NotBlank
    private Long authorId;

    public SaveBookRequest(String title, Long authorId, LocalDate publishedDate, String isbn) {
        this.title = title;
        this.authorId = authorId;
        this.publishedDate = publishedDate;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
