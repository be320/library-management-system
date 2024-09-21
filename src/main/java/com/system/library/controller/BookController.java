package com.system.library.controller;

import com.system.library.config.annotations.IsAdmin;
import com.system.library.config.annotations.IsUser;
import com.system.library.dto.book.SaveBookRequest;
import com.system.library.dto.book.BookDTO;
import com.system.library.dto.book.ViewBooksResponse;
import com.system.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/books")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping
    @IsUser
    public ResponseEntity<ViewBooksResponse> viewBooks(){
        ViewBooksResponse viewBooksResponse = bookService.viewBooks();
        return new ResponseEntity<>(viewBooksResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @IsUser
    public ResponseEntity<BookDTO> viewBookDetails(@PathVariable Long id){
        BookDTO bookDTO = bookService.viewBookDetails(id);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PostMapping
    @IsAdmin
    public ResponseEntity<BookDTO> addBook(@RequestBody SaveBookRequest addBookRequest){
        BookDTO bookDTO = bookService.addBook(addBookRequest);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @IsAdmin
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody SaveBookRequest updateBookRequest){
        BookDTO bookDTO = bookService.updateBook(id, updateBookRequest);
        return new ResponseEntity<>(bookDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @IsUser
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return new ResponseEntity<>("Deleted Successfully", HttpStatus.OK);
    }


}
