package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.AuthorReadDTO;

import java.util.List;

/**
 * Interface for the controller responsible for managing authors.
 */
public interface AuthorController {
    /**
     * Creates a new author in the database.
     *
     * @param authorCreateDTO The DTO object containing the data to create a new author.
     * @return The DTO object containing the data of the created author.
     */
    @Operation(summary = "Create a new author", description = "Create a new author in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    AuthorReadDTO createAuthor(AuthorCreateDTO authorCreateDTO);

    /**
     * Retrieves an author from the database by its unique ID.
     *
     * @param id The ID of the author.
     * @return The DTO object containing the data of the author.
     */
    @Operation(summary = "Get an author by ID", description = "Retrieve an author from the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author found successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    AuthorReadDTO getAuthorById(Integer id);

    /**
     * Updates an author in the database by its unique ID.
     *
     * @param id              The ID of the author.
     * @param authorCreateDTO The DTO object containing the new data for the author.
     * @return The DTO object containing the updated data of the author.
     */
    @Operation(summary = "Update an author by ID", description = "Update an author in the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body", content = @Content),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    AuthorReadDTO updateAuthorById(Integer id, AuthorCreateDTO authorCreateDTO);

    /**
     * Deletes an author from the database by its unique ID.
     *
     * @param id The ID of the author.
     */
    @Operation(summary = "Delete an author by ID", description = "Delete an author from the database by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Author not found", content = @Content)
    })
    void deleteAuthorById(Integer id);

    /**
     * Retrieves a list of all authors from the database.
     *
     * @return A list of DTO objects containing the data of all authors.
     */
    @Operation(summary = "Get all authors", description = "Retrieve a list of all authors from the database.")
    @ApiResponse(responseCode = "200", description = "Authors retrieved successfully")
    List<AuthorCreateDTO> getAllAuthors();
}