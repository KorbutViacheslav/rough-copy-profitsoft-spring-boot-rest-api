package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 19.04.2024
 */

/**
 * Service interface for managing Author entities.
 * Provides methods for creating, retrieving, updating, and deleting authors.
 */
public interface AuthorService {
    /**
     * Creates a new author.
     *
     * @param author The author to create.
     * @return The created author.
     */
    Author createAuthor(Author author);

    /**
     * Retrieves an author by ID.
     *
     * @param id The ID of the author to retrieve.
     * @return The retrieved author, or null if not found.
     */
    Author getAuthorById(Integer id);

    /**
     * Updates an existing author.
     *
     * @param id     The ID of the author to update.
     * @param author The updated author data.
     * @return The updated author.
     */
    Author updateAuthor(Integer id, Author author);

    /**
     * Deletes an author by ID.
     *
     * @param id The ID of the author to delete.
     * @return True if the author was successfully deleted, otherwise false.
     */
    boolean deleteAuthorById(Integer id);

    /**
     * Retrieves all authors.
     *
     * @return A list of all authors.
     */
    List<Author> getAllAuthor();

    /**
     * Finds an author by first name and last name.
     *
     * @param firstName The first name of the author.
     * @param lastName  The last name of the author.
     * @return The found author, or null if not found.
     */
    Author findByFirstNameAndLastName(String firstName, String lastName);

}
