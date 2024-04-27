package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.Optional;

/**
 * Author: Viacheslav Korbut
 * Date: 20.04.2024
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional <Author> findByFirstNameAndLastName(String firstName, String lastName);
}
