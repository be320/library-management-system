package com.system.library.mapper;

import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.SaveAuthorRequest;
import com.system.library.model.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO toDTO(Author author);

    Author toEntity(SaveAuthorRequest saveAuthorRequest);
}

