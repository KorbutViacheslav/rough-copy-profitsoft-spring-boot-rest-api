package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.BookRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceNotFoundException;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 18.04.2024
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Book createBook(Book book) {
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceIsExistException();
        }
    }

    @Override
    public Book getBookById(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Book updateBook(Integer id, Book book) {
        Book existingBook = getBookById(id);

        Author existingAuthor = existingBook.getAuthor();
        Author updatedAuthor = book.getAuthor();

        if (updatedAuthor != null && !updatedAuthor.equals(existingAuthor)) {
            existingBook.setAuthor(updatedAuthor);
        }
        existingBook.setTitle(book.getTitle());
        existingBook.setYearPublished(book.getYearPublished());
        existingBook.setGenres(book.getGenres());
        return bookRepository.save(existingBook);
    }

    @Override
    public boolean deleteBookById(Integer id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
