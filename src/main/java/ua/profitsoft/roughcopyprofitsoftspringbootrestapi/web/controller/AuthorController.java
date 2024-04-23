package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.AuthorReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;

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

    @PostMapping("/author")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorReadDTO createAuthor(@RequestBody @Valid AuthorReadDTO authorReadDTO) {
        Author author = authorMapper.toAuthor(authorReadDTO);
        authorService.createAuthor(author);
        return authorReadDTO;
    }

    @GetMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorReadDTO getAuthorById(@PathVariable Integer id) {
        Author author = authorService.getAuthorById(id);
        return authorMapper.toAuthorReadDTO(author);
    }

    @PatchMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorReadDTO updateAuthorById(@PathVariable Integer id, @RequestBody @Valid AuthorReadDTO authorReadDTO) {
        authorService.getAuthorById(id);
        Author author = authorMapper.toAuthor(authorReadDTO);
        return authorMapper.toAuthorReadDTO(authorService.updateAuthor(author));
    }

    @DeleteMapping("/author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAuthorById(@PathVariable Integer id) {
        authorService.deleteAuthorById(id);
    }
}
