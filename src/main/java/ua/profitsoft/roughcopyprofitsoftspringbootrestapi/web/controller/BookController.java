package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.BookMapper;

import java.util.List;

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

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReadDTO createBook(@RequestBody @Valid BookReadDTO bookReadDTO) {
        Book book = bookMapper.toBook(bookReadDTO);
        bookService.createBook(book);
        return bookReadDTO;
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @GetMapping("/books")
    @ResponseStatus(HttpStatus.OK)
    public List<BookReadDTO> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return bookMapper.toBookReadDTOList(books);
    }
}
