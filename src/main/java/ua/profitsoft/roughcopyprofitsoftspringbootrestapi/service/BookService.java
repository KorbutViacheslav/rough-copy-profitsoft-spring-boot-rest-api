package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service;

import org.springframework.data.domain.Page;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;

import java.util.List;
import java.util.Map;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public interface BookService {
    BookReadDTO createBook(BookCreateDTO book);

    BookReadDTO getBookById(Integer id);

    BookReadDTO updateBook(Integer id, BookCreateDTO bookCreateDTO);

    boolean deleteBookById(Integer id);

    Page<BookCreateDTO> findAllBooks(BookFilterRequest bookFilterRequest);

    Map<String, Object> uploadBooks(List<BookCreateDTO> bookCreateDTOs);
}
