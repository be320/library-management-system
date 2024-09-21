package com.system.library.service;

import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.ViewAuthorsResponse;
import com.system.library.dto.author.AuthorDTO;
import com.system.library.dto.author.SaveAuthorRequest;
import com.system.library.exception.EntityNotFoundException;
import com.system.library.mapper.AuthorMapper;
import com.system.library.model.AuditLog;
import com.system.library.model.Author;
import com.system.library.model.Author;
import com.system.library.model.User;
import com.system.library.repository.AuditLogRepository;
import com.system.library.repository.AuthorRepository;
import com.system.library.repository.UserRepository;
import com.system.library.util.enums.EntityEnum;
import com.system.library.util.enums.OperationEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    UserRepository userRepository;

    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    AuthorMapper authorMapper;

    @Autowired
    TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

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

    public AuthorDTO addAuthor(SaveAuthorRequest addAuthorRequest){
        Author authorToSave = authorMapper.toEntity(addAuthorRequest);
        Author author = authorRepository.save(authorToSave);

        String username =  tokenService.getUsernameFromToken();
        Optional<User> user =  userRepository.findByUsername(username);
        AuditLog auditLog = new AuditLog(EntityEnum.AUTHOR.name(), author.getId(), OperationEnum.CREATE.name(), user.get().getId());
        auditLogRepository.save(auditLog);
        auditLog.printDetails();

        return authorMapper.toDTO(author);
    }

    public AuthorDTO updateAuthor(Long id, SaveAuthorRequest updateAuthorRequest){

        Optional<Author> authorOptional =  authorRepository.findById(id);
        if(authorOptional.isPresent()){
            Author author = authorOptional.get();
            author.setBiography(updateAuthorRequest.getBiography());
            author.setDob(updateAuthorRequest.getDob());
            author.setName(updateAuthorRequest.getName());
            authorRepository.save(author);
            return authorMapper.toDTO(author);
        }
        else
            throw new EntityNotFoundException();
    }

    public void deleteAuthor(Long id){
        Optional<Author> author =  authorRepository.findById(id);
        if(author.isEmpty())
            throw new EntityNotFoundException();

        authorRepository.delete(author.get());
    }

}
