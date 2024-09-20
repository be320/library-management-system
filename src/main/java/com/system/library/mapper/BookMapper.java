package com.system.library.mapper;

import com.system.library.dto.book.AddBookRequest;
import com.system.library.dto.book.BookDTO;
import com.system.library.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDTO(Book book);

    Book toEntity(AddBookRequest addBookRequest);
}

