package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public interface BookService {
    Book createBook(Book book);

    Book getBookById(Integer id);

    Book updateBook(Book book);

    boolean deleteBookById(Integer id);

    List<Book> getAllBooks();
}
