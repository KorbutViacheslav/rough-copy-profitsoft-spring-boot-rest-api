package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.AuthorReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final BookService bookService;

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new author", description = "Create new author to database.")
    public AuthorReadDTO createAuthor(@RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        authorService.createAuthor(author);
        return authorMapper.toAuthorReadDTO(author);
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get author by ID", description = "Get an author from the database by its unique ID.")
    public AuthorReadDTO getAuthorById(@PathVariable Integer id) {
        Author author = authorService.getAuthorById(id);
        return authorMapper.toAuthorReadDTO(author);
    }

    @PatchMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update author by ID", description = "Update an author from the database by its unique ID.")
    public AuthorReadDTO updateAuthorById(@PathVariable Integer id, @RequestBody @Valid AuthorCreateDTO authorCreateDTO) {
        Author author = authorMapper.toAuthor(authorCreateDTO);
        return authorMapper.toAuthorReadDTO(authorService.updateAuthor(id, author));
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete author by ID", description = "Delete an author from the database by its unique ID.")
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all authors", description = "Get all authors from database.")
    public List<AuthorCreateDTO> getAllAuthors() {
        return authorMapper.toListAuthorCreateDTO(authorService.getAllAuthor());
    }
}
