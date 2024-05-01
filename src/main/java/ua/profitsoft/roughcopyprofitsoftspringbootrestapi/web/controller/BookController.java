package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;

import java.util.Map;
/**
 * Author: Viacheslav Korbut
 * Date: 01.05.2024
 */

/**
 * Interface for the controller responsible for managing books.
 */
public interface BookController {
    /**
     * Creates a new book in the database.
     *
     * @param bookCreateDTO The DTO object containing the data to create a new book.
     * @return The DTO object containing the data of the created book.
     */
    @Operation(summary = "Create a new book", description = "Create a new book in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content)
    })
    BookReadDTO createBook(BookCreateDTO bookCreateDTO);

    /**
     * Retrieves a book from the database by its unique ID.
     *
     * @param id The ID of the book.
     * @return The DTO object containing the data of the book.
     */
    @Operation(summary = "Get a book by ID", description = "Retrieve a book from the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    BookReadDTO getBookById(Integer id);

    /**
     * Updates a book in the database by its unique ID.
     *
     * @param id            The ID of the book.
     * @param bookCreateDTO The DTO object containing the new data for the book.
     * @return The DTO object containing the updated data of the book.
     */
    @Operation(summary = "Update a book by ID", description = "Update a book in the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    BookReadDTO updateBookById(Integer id, BookCreateDTO bookCreateDTO);

    /**
     * Deletes a book from the database by its unique ID.
     *
     * @param id The ID of the book.
     */
    @Operation(summary = "Delete a book by ID", description = "Delete a book from the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found", content = @Content)
    })
    void deleteBookById(Integer id);

    /**
     * Retrieves a page of books based on the given filters.
     *
     * @param request The request object containing the filter parameters.
     * @return A page of DTO objects containing the data of the filtered books.
     */
    @Operation(summary = "Get a page of books by filters", description = "Get a page of books by filters: title, year publish, author name, author last name. Get page and size. You can choose one or many filters.")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully")
    Page<BookCreateDTO> page(BookFilterRequest request);

    /**
     * Generates a report in Excel or CSV format for books based on the given filters.
     *
     * @param request The request object containing the filter parameters.
     * @return A ResponseEntity containing the generated report as a Resource.
     */
    @Operation(summary = "Generate a report for books", description = "Generate a report in Excel or CSV format for books based on given filters")
    @ApiResponse(responseCode = "200", description = "Report generated successfully")
    ResponseEntity<Resource> generateReport(BookFilterRequest request);

    /**
     * Uploads books from a JSON file and saves valid entries to the database.
     *
     * @param file The MultipartFile containing the JSON file.
     * @return A ResponseEntity containing a Map with the response data.
     */
    @Operation(summary = "Upload books from JSON file", description = "Upload books from JSON file and save valid entries to the database.")
    @ApiResponse(responseCode = "200", description = "Books uploaded successfully")
    ResponseEntity<Map<String, Object>> uploadBooks(MultipartFile file);
}
