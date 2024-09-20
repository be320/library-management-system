package com.system.library.dto.book;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.library.model.Author;
import com.system.library.model.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    private LocalDate publishedDate;

    private Author author;

    private Member member;

    public BookDTO(Long id, String title, String isbn, LocalDate publishedDate, Author author, Member member) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.author = author;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
