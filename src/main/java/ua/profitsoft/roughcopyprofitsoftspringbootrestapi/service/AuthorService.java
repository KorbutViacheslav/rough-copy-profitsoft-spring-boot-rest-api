package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import org.springframework.data.domain.Page;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.List;
import java.util.Optional;

/**
 * Author: Viacheslav Korbut
 * Date: 19.04.2024
 */
public interface AuthorService {

    Author createAuthor(Author author);

    Author getAuthorById(Integer id);

    Author updateAuthor(Integer id, Author author);

    boolean deleteAuthorById(Integer id);

    List<Author> getAllAuthor();

    Author findByFirstNameAndLastName(String firstName, String lastName);

}
