package com.system.library.service;

import com.system.library.dto.book.BookDTO;
import com.system.library.dto.book.ViewBooksResponse;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.mapper.BookMapper;
import com.system.library.model.Book;
import com.system.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookMapper bookMapper;

    public ViewBooksResponse viewBooks(){
        List<BookDTO> booksDTO = new ArrayList<>();
        List<Book> books = bookRepository.findAll();
        books.forEach(book -> booksDTO.add(bookMapper.toDTO(book)));
        return new ViewBooksResponse(booksDTO);
    }

    public BookDTO viewBookDetails(Long id){
        Optional<Book> book =  bookRepository.findById(id);
        if(book.isEmpty())
            throw new EntityNotFoundException();

        return bookMapper.toDTO(book.get());
    }
}
