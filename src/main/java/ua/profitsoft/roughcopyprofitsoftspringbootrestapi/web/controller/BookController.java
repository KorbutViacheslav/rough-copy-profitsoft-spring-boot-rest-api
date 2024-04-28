package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.BookMapper;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.writer.CSVReportGenerator;

import java.io.IOException;
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
    private final BookMapper bookMapper;
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping("/book")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create new book", description = "Create new book to database.")
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
    @Operation(summary = "Get book by ID", description = "Get a book from the database by its unique ID.")
    public BookReadDTO getBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        return bookMapper.toBookReadDTO(book);
    }

    @PatchMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update book by ID", description = "Update a book from the database by its unique ID.")
    public BookReadDTO updateBookById(@PathVariable Integer id, @RequestBody @Valid BookCreateDTO bookCreateDTO) {
        Book b = bookMapper.toBook(bookCreateDTO);
        Book bo = bookService.updateBook(id, b);

        return bookMapper.toBookReadDTO(bo);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete book by ID", description = "Delete a book from the database by its unique ID.")
    public void deleteBookById(@PathVariable Integer id) {
        bookService.deleteBookById(id);
    }

/*    @PostMapping("/page")
    @ResponseStatus(HttpStatus.OK)
    public Page<BookCreateDTO> getPageWithBooks(@RequestBody BookFilterRequest request) {
        Page<Book> bookPage = bookService.findAllBooks(
                request.getTitle(),
                request.getYearPublish(),
                request.getPage(),
                request.getSize());
        List<BookCreateDTO> bookCreateDTOList = bookPage.getContent().stream()
                .map(bookMapper::toBookCreateDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(bookCreateDTOList, bookPage.getPageable(), bookPage.getTotalElements());
    }*/

    @PostMapping("/pageable")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get page books by filters", description = "Get page books by filters: title, year publish, author name, author last name. Get page and size. You make choose one or many filters.")
    public Page<BookCreateDTO> page(@RequestBody BookFilterRequest request) {
        Page<Book> bookPage = bookService.findAllBooks(request);
        List<BookCreateDTO> bookCreateDTOList = bookPage.getContent().stream()
                .map(bookMapper::toBookCreateDTO)
                .toList();
        return new PageImpl<>(bookCreateDTOList, bookPage.getPageable(), bookPage.getTotalElements());
    }

    @PostMapping("/_report")
    @Operation(summary = "Generate report for books", description = "Generate report in Excel or CSV format for books based on given filters")
    public ResponseEntity<Resource> generateReport(@RequestBody BookFilterRequest request) {
        List<BookCreateDTO> bookCreateDTOList = bookService.findAllBooks(request).getContent().stream()
                .map(bookMapper::toBookCreateDTO)
                .toList();

        CSVReportGenerator csvReportGenerator = new CSVReportGenerator();
        ByteArrayResource resource = csvReportGenerator.generateCSVReport(bookCreateDTOList);

        if (resource != null) {
            String fileName = "books_report.csv";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/book/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload books from JSON file", description = "Upload books from JSON file and save valid entries to the database.")
    public ResponseEntity<Map<String, Object>> uploadBooks(@RequestPart("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "File is empty"));
        }

        try {
            List<BookCreateDTO> bookCreateDTOs = parseJsonFile(file);
            Map<String, Object> response = bookService.uploadBooks(bookCreateDTOs);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to read file"));
        }
    }

    private List<BookCreateDTO> parseJsonFile(MultipartFile file) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<BookCreateDTO>> typeRef = new TypeReference<>() {};
        return objectMapper.readValue(file.getInputStream(), typeRef);
    }

}
