package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 19.04.2024
 */
public interface AuthorService {

    Author createAuthor(Author author);

    Author getAuthorById(Integer id);

    Author updateAuthor(Author author);

    boolean deleteAuthorById(Integer id);

    List<Author> getAllAuthor();
}
