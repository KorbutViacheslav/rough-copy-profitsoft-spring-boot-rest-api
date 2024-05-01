package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Repository interface for managing Book entities.
 * Provides methods for performing CRUD operations and querying the database.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer>, JpaSpecificationExecutor<Book> {
    /**
     * Checks if a book with the given title and author exists.
     *
     * @param title  The title of the book.
     * @param author The author of the book.
     * @return True if a book with the given title and author exists, otherwise false.
     */
    Boolean existsByTitleAndAuthor(String title, Author author);
}
