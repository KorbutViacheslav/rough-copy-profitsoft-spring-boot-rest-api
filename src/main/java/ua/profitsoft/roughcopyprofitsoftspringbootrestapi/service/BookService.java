package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import org.springframework.data.domain.Page;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;

import java.util.List;
import java.util.Map;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public interface BookService {
    Book createBook(Book book);

    Book getBookById(Integer id);

    Book updateBook(Integer id, Book book);

    boolean deleteBookById(Integer id);

    //List<Book> getAllBooks();

    //Page<Book> findAllBooks(String title, Integer yearPublish,Integer page, Integer size);
    Page<Book> findAllBooks(BookFilterRequest bookFilterRequest);

    Map<String, Object> uploadBooks(List<BookCreateDTO> bookCreateDTOs);
}
