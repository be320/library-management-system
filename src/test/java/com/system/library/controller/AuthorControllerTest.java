package com.system.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.system.library.dto.author.SaveAuthorRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private String jwtToken;

    // Method to get JWT token
    private String getJwtToken() throws Exception {
        String loginRequest = "{ \"username\": \"admin\", \"password\": \"secret\" }";

        MvcResult result = mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        // Extract the token from the response (assuming it's returned in the response)
        // Adjust the parsing logic based on your response structure
        return new ObjectMapper().readTree(responseContent).get("token").asText();
    }

    @Test
    void setup() throws Exception {
        jwtToken = getJwtToken();
    }

    // Test for viewing all authors
    @Test
    void shouldReturnListOfAuthors() throws Exception {
        mockMvc.perform(get("/authors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Test for viewing author details
    @Test
    void shouldReturnAuthorDetails() throws Exception {
        Long authorId = 1L; // Example ID

        mockMvc.perform(get("/authors/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId))
                .andExpect(jsonPath("$.name").exists());
    }

    // Test for adding an author (Admin-only access)
    @Test
    void shouldAddAuthor() throws Exception {
        SaveAuthorRequest newAuthor = new SaveAuthorRequest(
                "Ahmed Bahaa",
                "Software Engineer born in Egypt",
                LocalDate.parse("1998-02-15")
        );

        mockMvc.perform(post("/authors")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Author Name"));
    }

    // Test for updating an author (Admin-only access)
    @Test
    void shouldUpdateAuthor() throws Exception {
        Long authorId = 1L; // Example ID
        SaveAuthorRequest updatedAuthor = new SaveAuthorRequest(
                "Ahmed Bahaa Ibrahim",
                "Software Engineer born in Egypt",
                LocalDate.parse("1998-02-15")
        );

        mockMvc.perform(put("/authors/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedAuthor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Author Name"));
    }

    // Test for deleting an author (Admin-only access)
    @Test
    void shouldDeleteAuthor() throws Exception {
        Long authorId = 1L; // Example ID

        mockMvc.perform(delete("/authors/{id}", authorId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Successfully"));
    }

    // Helper method to convert objects to JSON string
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
