package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.BookMapper;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDTO createBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        Author author = authorMapper.toAuthor(bookCreateDTO.getAuthor());
        Book book = bookMapper.toBook(bookCreateDTO);

        try {
            authorService.createAuthor(author);
        } catch (ResourceIsExistException ex) {
            author = authorService.findByFirstNameAndLastName(
                    bookCreateDTO.getAuthor().getFirstName(),
                    bookCreateDTO.getAuthor().getLastName());
        }
        book.setAuthor(author);
        bookService.createBook(book);
        return bookMapper.toBookReadDTO(book);
    }

    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookReadDTO getBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        return bookMapper.toBookReadDTO(book);
    }

    @PatchMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BookReadDTO updateBookById(@PathVariable Integer id, @RequestBody @Valid BookReadDTO bookReadDTO) {
        bookService.getBookById(id);
        Book book = bookMapper.toBook(bookReadDTO);
        return bookMapper.toBookReadDTO(bookService.updateBook(book));
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }
}
