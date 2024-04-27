package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.BookRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookFilterRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter.BookSpecification;

/**
 * Author: Viacheslav Korbut
 * Date: 18.04.2024
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

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

        // Перевірка, чи було змінено автора
        if (book.getAuthor() != null && !book.getAuthor().equals(existingBook.getAuthor())) {
            // Перевірити, чи існує автор з таким іменем і прізвищем
            Optional<Author> optionalAuthor = authorRepository.findByFirstNameAndLastName(book.getAuthor().getFirstName(), book.getAuthor().getLastName());
            if (optionalAuthor.isPresent()) {
                // Якщо автор вже існує, присвоїти його існуючій книзі
                existingBook.setAuthor(optionalAuthor.get());
            } else {
                // Якщо автора не існує, зберегти нового автора
                book.setAuthor(authorRepository.save(book.getAuthor()));
            }
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

    @Override
    public Page<Book> findAllBooks(String title, Integer yearPublish, Integer page, Integer size) {
        Specification<Book> specification = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
            }
            if (yearPublish != null) {
                predicates.add(criteriaBuilder.equal(root.get("yearPublished"), yearPublish));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.findAll(specification, pageable);
    }

    @Override
    public Page<Book> findAllBooks(BookFilterRequest bookFilterRequest) {
        Specification<Book> specification = new BookSpecification(bookFilterRequest);
        Pageable pageable = PageRequest.of(bookFilterRequest.getPage(), bookFilterRequest.getSize());
        return bookRepository.findAll(specification, pageable);
    }

}
