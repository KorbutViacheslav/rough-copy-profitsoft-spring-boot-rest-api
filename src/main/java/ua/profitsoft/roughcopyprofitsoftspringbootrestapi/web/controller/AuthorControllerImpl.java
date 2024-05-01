package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.AuthorReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */

/**
 * Implementation of the controller responsible for managing authors.
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorControllerImpl implements AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    /**
     * {@inheritDoc}
     */
    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public AuthorReadDTO createAuthor(@RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        authorService.createAuthor(author);
        return authorMapper.toAuthorReadDTO(author);
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorReadDTO getAuthorById(@PathVariable Integer id) {
        Author author = authorService.getAuthorById(id);
        return authorMapper.toAuthorReadDTO(author);
    }

    /**
     * {@inheritDoc}
     */
    @PatchMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public AuthorReadDTO updateAuthorById(@PathVariable Integer id, @RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        return authorMapper.toAuthorReadDTO(authorService.updateAuthor(id, author));
    }

    /**
     * {@inheritDoc}
     */
    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

    /**
     * {@inheritDoc}
     */
    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public List<AuthorCreateDTO> getAllAuthors() {
        return authorMapper.toListAuthorCreateDTO(authorService.getAllAuthor());
    }
}
