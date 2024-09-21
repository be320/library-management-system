package com.system.library.dto.member;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public class SaveMemberRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private LocalDate membershipDate;


    public SaveMemberRequest(String name, String email, LocalDate membershipDate) {
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
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
}
