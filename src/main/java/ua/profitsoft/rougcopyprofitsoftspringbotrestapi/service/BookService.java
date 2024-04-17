package ua.profitsoft.rougcopyprofitsoftspringbotrestapi.service;

import ua.profitsoft.rougcopyprofitsoftspringbotrestapi.model.Book;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public interface BookService {
    Book createBook();

    Book getBookById(Integer id);

    Book updateBook();

    boolean deleteBookById();

    List<Book> getAllBooks();
}
