package com.system.library.service;

import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.ViewAuthorsResponse;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.mapper.AuthorMapper;
import com.system.library.model.Author;
import com.system.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    AuthorMapper authorMapper;

    public ViewAuthorsResponse viewAuthors(){
        List<AuthorDTO> authorsDTO = new ArrayList<>();
        List<Author> authors = authorRepository.findAll();
        authors.forEach(author -> authorsDTO.add(authorMapper.toDTO(author)));
        return new ViewAuthorsResponse(authorsDTO);
    }

    public AuthorDTO viewAuthorDetails(Long id){
        Optional<Author> author =  authorRepository.findById(id);
        if(author.isEmpty())
            throw new EntityNotFoundException();

        return authorMapper.toDTO(author.get());
    }

}
