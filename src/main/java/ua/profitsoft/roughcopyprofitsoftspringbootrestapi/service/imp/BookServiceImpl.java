package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.imp;

import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 18.04.2024
 */
@Service
public class BookServiceImpl implements BookService {
    @Override
    public Book createBook() {
        return null;
    }

    @Override
    public Book getBookById(Integer id) {
        return null;
    }

    @Override
    public Book updateBook() {
        return null;
    }

    @Override
    public boolean deleteBookById() {
        return false;
    }

    @Override
    public List<Book> getAllBooks() {
        return List.of();
    }
}
