package com.system.library.service;

import com.system.library.dto.book.SaveBookRequest;
import com.system.library.dto.book.BookDTO;
import com.system.library.dto.book.ViewBooksResponse;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.mapper.BookMapper;
import com.system.library.model.Author;
import com.system.library.model.Book;
import com.system.library.model.User;
import com.system.library.repository.AuthorRepository;
import com.system.library.repository.BookRepository;
import com.system.library.util.enums.EntityEnum;
import com.system.library.util.enums.OperationEnum;
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
    AuthorRepository authorRepository;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    AuditLogService auditLogService;

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

    public BookDTO addBook(SaveBookRequest addBookRequest){
        Optional<Author> author = authorRepository.findById(addBookRequest.getAuthorId());
        if(author.isEmpty())
            throw new EntityNotFoundException();
        Book bookToSave = bookMapper.toEntity(addBookRequest);
        bookToSave.setAuthor(author.get());
        Book book = bookRepository.save(bookToSave);
        auditLogService.auditLog(EntityEnum.BOOK, OperationEnum.CREATE, book.getId());
        return bookMapper.toDTO(book);
    }

    public BookDTO updateBook(Long id, SaveBookRequest updateBookRequest){

        Optional<Book> bookOptional =  bookRepository.findById(id);
        if(bookOptional.isPresent()){
            Book book = bookOptional.get();

            Optional<Author> authorOptional = authorRepository.findById(updateBookRequest.getAuthorId());
            if(authorOptional.isEmpty())
                throw new EntityNotFoundException();
            book.setAuthor(authorOptional.get());

            book.setIsbn(updateBookRequest.getIsbn());
            book.setPublishedDate(updateBookRequest.getPublishedDate());
            book.setTitle(updateBookRequest.getTitle());
            bookRepository.save(book);
            auditLogService.auditLog(EntityEnum.BOOK, OperationEnum.UPDATE, book.getId());
            return bookMapper.toDTO(book);
        }
        else
            throw new EntityNotFoundException();
    }

    public void deleteBook(Long id){
        Optional<Book> book =  bookRepository.findById(id);
        if(book.isEmpty())
            throw new EntityNotFoundException();

        bookRepository.delete(book.get());
        auditLogService.auditLog(EntityEnum.BOOK, OperationEnum.DELETE, book.get().getId());
    }
}
