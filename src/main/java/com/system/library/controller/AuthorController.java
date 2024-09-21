package com.system.library.controller;


import com.system.library.config.annotations.IsAdmin;
import com.system.library.config.annotations.IsUser;
import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.ViewAuthorsResponse;
import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.SaveAuthorRequest;
import com.system.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping
    @IsUser
    public ResponseEntity<ViewAuthorsResponse> viewAuthors(){
        ViewAuthorsResponse viewAuthorsResponse = authorService.viewAuthors();
        return new ResponseEntity<>(viewAuthorsResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @IsUser
    public ResponseEntity<AuthorDTO> viewAuthorDetails(@PathVariable Long id){
        AuthorDTO authorDTO = authorService.viewAuthorDetails(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody SaveAuthorRequest addAuthorRequest){
        AuthorDTO authorDTO = authorService.addAuthor(addAuthorRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id, @RequestBody SaveAuthorRequest updateAuthorRequest){
        AuthorDTO authorDTO = authorService.updateAuthor(id, updateAuthorRequest);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @IsAdmin
    public ResponseEntity<String> deleteAuthor(@PathVariable Long id){
        authorService.deleteAuthor(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }
}

