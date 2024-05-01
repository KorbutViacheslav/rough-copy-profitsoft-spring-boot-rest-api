package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.Optional;

/**
 * Author: Viacheslav Korbut
 * Date: 20.04.2024
 */

/**
 * Repository interface for managing Author entities.
 * Provides methods for performing CRUD operations and querying the database.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer>, JpaSpecificationExecutor<Author> {
    /**
     * Finds an author by their first name and last name.
     *
     * @param firstName The first name of the author.
     * @param lastName  The last name of the author.
     * @return An Optional containing the author if found, otherwise empty.
     */
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
