package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import org.springframework.data.domain.Page;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;

import java.util.List;
import java.util.Map;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Service interface for managing books.
 */
public interface BookService {
    /**
     * Create a new book.
     *
     * @param book the details of the book to be created
     * @return the newly created book with details
     */
    BookReadDTO createBook(BookCreateDTO book);

    /**
     * Retrieve a book by its unique identifier.
     *
     * @param id the unique identifier of the book to retrieve
     * @return the book with the specified identifier
     */
    BookReadDTO getBookById(Integer id);

    /**
     * Update the details of an existing book.
     *
     * @param id            the unique identifier of the book to be updated
     * @param bookCreateDTO the updated details of the book
     * @return the updated book with new details
     */
    BookReadDTO updateBook(Integer id, BookCreateDTO bookCreateDTO);

    /**
     * Delete a book by its unique identifier.
     *
     * @param id the unique identifier of the book to be deleted
     * @return true if the book was successfully deleted, otherwise false
     */
    boolean deleteBookById(Integer id);

    /**
     * Retrieve all books based on the provided filter criteria.
     *
     * @param bookFilterRequest the filter criteria for retrieving books
     * @return a page of books matching the filter criteria
     */
    Page<BookCreateDTO> findAllBooks(BookFilterRequest bookFilterRequest);

    /**
     * Uploads books from a JSON file and determines the number of successfully uploaded books to the database,
     * as well as the number of books that failed to upload along with the reason for failure.
     *
     * @param bookCreateDTOs the list of books to be uploaded
     * @return a map containing the count of successfully uploaded books and the count of failed uploads
     * along with the reasons for failure
     */
    Map<String, Object> uploadBooks(List<BookCreateDTO> bookCreateDTOs);
}
