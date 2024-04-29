package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.impl;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.BookRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.BookService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceNotFoundException;

import java.util.*;

import jakarta.persistence.criteria.Predicate;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.BookMapper;
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
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final Validator validator;

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
    public Page<BookCreateDTO> findAllBooks(BookFilterRequest bookFilterRequest) {
        Specification<Book> specification = new BookSpecification(bookFilterRequest);
        Pageable pageable = PageRequest.of(bookFilterRequest.getPage(), bookFilterRequest.getSize());
        Page<Book> bookPage = bookRepository.findAll(specification, pageable);
        return bookPage.map(bookMapper::toBookCreateDTO);
    }

    public Map<String, Object> uploadBooks(List<BookCreateDTO> bookCreateDTOs) {
        Map<String, Object> response = new HashMap<>();
        List<BookCreateDTO> successfullyImported = new ArrayList<>();
        List<String> failedToImport = new ArrayList<>();

        for (BookCreateDTO bookCreateDTO : bookCreateDTOs) {
            try {
                if (!isBookCreateDTOValid(bookCreateDTO, failedToImport)) {
                    continue;
                }

                Author author = getAuthor(bookCreateDTO, failedToImport);
                if (author == null) {
                    continue;
                }

                Book book = bookMapper.toBook(bookCreateDTO);
                book.setAuthor(author);

                if (isBookDuplicate(book, failedToImport)) {
                    continue;
                }

                bookRepository.save(book);
                successfullyImported.add(bookCreateDTO);
            } catch (Exception e) {
                String errorMessage = e.getMessage();
                failedToImport.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: " + errorMessage);
            }
        }

        response.put("successfullyImported", successfullyImported.size());
        response.put("failedToImport", failedToImport);

        return response;
    }

    private boolean isBookCreateDTOValid(BookCreateDTO bookCreateDTO, List<String> failedToImport) {
        Set<ConstraintViolation<BookCreateDTO>> violations = validator.validate(bookCreateDTO);
        if (!violations.isEmpty()) {
            StringBuilder violationMessages = new StringBuilder();
            for (ConstraintViolation<BookCreateDTO> violation : violations) {
                violationMessages.append(violation.getMessage()).append("; ");
            }
            failedToImport.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: " + violationMessages);
            return false;
        }
        return true;
    }

    private Author getAuthor(BookCreateDTO bookCreateDTO, List<String> failedToImport) {
        Author author = authorMapper.toAuthor(bookCreateDTO.getAuthor());
        Optional<Author> existingAuthor = authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName());
        if (existingAuthor.isEmpty()) {
            failedToImport.add("Failed to import book: " + bookCreateDTO.getTitle() + ". Reason: Author not found in the database.");
            return null;
        }
        return existingAuthor.get();
    }

    private boolean isBookDuplicate(Book book, List<String> failedToImport) {
        if (bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor())) {
            failedToImport.add("Failed to import book: " + book.getTitle() + ". Reason: Duplicate book entry in the database.");
            return true;
        }
        return false;
    }

}
