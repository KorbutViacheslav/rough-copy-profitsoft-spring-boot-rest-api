package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.RoughCopyProfitsoftSpringBootRestApiApplication;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.BookRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Author: Viacheslav Korbut
 * Date: 30.04.2024
 */
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RoughCopyProfitsoftSpringBootRestApiApplication.class)
@AutoConfigureMockMvc
class BookControllerImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private List<Book> books;

    @BeforeEach
    void setUp() {
        books = createTestBooks();
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    void shouldCreateBook() throws Exception {
        AuthorCreateDTO authorCreateDTO = new AuthorCreateDTO("John", "Doe");
        Set<String> genres = new HashSet<>(List.of("Horror", "Thriller"));
        BookCreateDTO bookCreateDTO = new BookCreateDTO("New Book", 2022, authorCreateDTO, genres);

        mockMvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(bookCreateDTO.getTitle()))
                .andExpect(jsonPath("$.yearPublished").value(bookCreateDTO.getYearPublished()))
                .andExpect(jsonPath("$.authorCreateDTO.firstName").value(authorCreateDTO.getFirstName()))
                .andExpect(jsonPath("$.authorCreateDTO.lastName").value(authorCreateDTO.getLastName()))
                .andExpect(jsonPath("$.genres", containsInAnyOrder(genres.toArray())));
    }

    @Test
    void shouldGetBookById() throws Exception {
        Book book = books.get(0);
        BookReadDTO expectedBookReadDTO = new BookReadDTO(
                book.getId(),
                book.getTitle(),
                new AuthorCreateDTO(book.getAuthor().getFirstName(), book.getAuthor().getLastName()),
                book.getYearPublished(),
                book.getGenres()
        );

        mockMvc.perform(get("/api/book/{id}", book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedBookReadDTO.getId()))
                .andExpect(jsonPath("$.title").value(expectedBookReadDTO.getTitle()))
                .andExpect(jsonPath("$.authorCreateDTO.firstName").value(expectedBookReadDTO.getAuthorCreateDTO().getFirstName()))
                .andExpect(jsonPath("$.authorCreateDTO.lastName").value(expectedBookReadDTO.getAuthorCreateDTO().getLastName()))
                .andExpect(jsonPath("$.yearPublished").value(expectedBookReadDTO.getYearPublished()))
                .andExpect(jsonPath("$.genres", containsInAnyOrder(expectedBookReadDTO.getGenres().toArray())));
    }

    @Test
    void shouldUpdateBookById() throws Exception {
        Book book = books.get(0);
        AuthorCreateDTO updatedAuthorCreateDTO = new AuthorCreateDTO("Jane", "Smith");
        Set<String> updatedGenres = new HashSet<>(List.of("Mystery", "Thriller"));
        BookCreateDTO updatedBookCreateDTO = new BookCreateDTO("Updated Book", 2021, updatedAuthorCreateDTO, updatedGenres);

        mockMvc.perform(patch("/api/book/{id}", book.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBookCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatedBookCreateDTO.getTitle()))
                .andExpect(jsonPath("$.yearPublished").value(updatedBookCreateDTO.getYearPublished()))
                .andExpect(jsonPath("$.authorCreateDTO.firstName").value(updatedAuthorCreateDTO.getFirstName()))
                .andExpect(jsonPath("$.authorCreateDTO.lastName").value(updatedAuthorCreateDTO.getLastName()))
                .andExpect(jsonPath("$.genres", containsInAnyOrder(updatedGenres.toArray())));
    }

    @Test
    void shouldDeleteBookById() throws Exception {
        Book book = books.get(0);
        mockMvc.perform(delete("/api/book/{id}", book.getId()))
                .andExpect(status().isOk());
    }

    private List<Book> createTestBooks() {
        Author author1 = authorRepository.save(new Author("John", "Doe"));
        Author author2 = authorRepository.save(new Author("Jane", "Smith"));

        Set<String> genres1 = new HashSet<>(List.of("Horror", "Thriller"));
        Set<String> genres2 = new HashSet<>(List.of("Mystery", "Crime"));

        return List.of(
                bookRepository.save(new Book("Book 1", author1, 2022, genres1)),
                bookRepository.save(new Book("Book 2", author1, 2022, genres1)),
                bookRepository.save(new Book("Book 3", author2, 2021, genres2))
        );
    }

}