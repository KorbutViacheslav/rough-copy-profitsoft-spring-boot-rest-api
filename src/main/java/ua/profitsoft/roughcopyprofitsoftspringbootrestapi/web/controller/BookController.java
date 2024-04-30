package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.parser.BookCreateJsonFileParser;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.writer.CSVReportGenerator;

import java.util.List;
import java.util.Map;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new book", description = "Create new book to database.")
    public BookReadDTO createBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.createBook(bookCreateDTO);
    }

    @GetMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get book by ID", description = "Get a book from the database by its unique ID.")
    public BookReadDTO getBookById(@PathVariable Integer id) {
        return bookService.getBookById(id);

    }

    @PatchMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update book by ID", description = "Update a book from the database by its unique ID.")
    public BookReadDTO updateBookById(@PathVariable Integer id, @RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.updateBook(id, bookCreateDTO);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete book by ID", description = "Delete a book from the database by its unique ID.")
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

    @PostMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get page books by filters", description = "Get page books by filters: title, year publish, author name, author last name. Get page and size. You make choose one or many filters.")
    public Page<BookCreateDTO> page(@RequestBody BookFilterRequest request) {
        return bookService.findAllBooks(request);
    }

    @PostMapping("/_report")
    @Operation(summary = "Generate report for books", description = "Generate report in Excel or CSV format for books based on given filters")
    public ResponseEntity<Resource> generateReport(@RequestBody BookFilterRequest request) {
        List<BookCreateDTO> bookCreateDTOList = bookService.findAllBooks(request).getContent();
        ByteArrayResource resource = CSVReportGenerator.generateCSVReport(bookCreateDTOList);
        String fileName = CSVReportGenerator.generateFileName();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);

    }

    @PostMapping(value = "/book/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload books from JSON file", description = "Upload books from JSON file and save valid entries to the database.")
    public ResponseEntity<Map<String, Object>> uploadBooks(@RequestPart("file") MultipartFile file) {
        List<BookCreateDTO> bookCreateDTOs = BookCreateJsonFileParser.parseJsonFile(file);
        Map<String, Object> response = bookService.uploadBooks(bookCreateDTOs);
        return ResponseEntity.ok(response);
    }

}
