package com.system.library.dto.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.system.library.model.Author;
import com.system.library.model.Book;
import com.system.library.model.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    private LocalDate membershipDate;

    private List<Book> borrowedBooks = new ArrayList<>();

    public MemberDTO(String name, String email, LocalDate membershipDate, List<Book> borrowedBooks) {
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
        this.borrowedBooks = borrowedBooks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(List<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}
