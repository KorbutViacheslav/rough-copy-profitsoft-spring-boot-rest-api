package ua.profitsoft.web.controller;

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
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper.AuthorMapper;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = RoughCopyProfitsoftSpringBootRestApiApplication.class)
@AutoConfigureMockMvc
class AuthorControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    private List<Author> authors;

    @BeforeEach
    void setUp() {
        authors = IntStream.range(1, 4)
                .mapToObj(i -> new Author("FirstName" + i, "LastName" + i))
                .collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @AfterEach
    void afterEach() {
        authorRepository.deleteAll();
    }

    @Test
    void shouldCreateAuthor() throws Exception {
        AuthorCreateDTO authorCreateDTO = new AuthorCreateDTO("NewFirstName", "NewLastName");
        mockMvc.perform(post("/api/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authorCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value(authorCreateDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(authorCreateDTO.getLastName()));
    }

    @Test
    void shouldGetAuthorById() throws Exception {
        Author author = authors.get(0);
        mockMvc.perform(get("/api/author/{id}", author.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.firstName").value(author.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(author.getLastName()));
    }

    @Test
    void shouldUpdateAuthor() throws Exception {
        Author author = authors.get(0);
        AuthorCreateDTO updatedAuthorCreateDTO = new AuthorCreateDTO("UpdatedFirstName", "UpdatedLastName");
        mockMvc.perform(patch("/api/author/{id}", author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAuthorCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(author.getId()))
                .andExpect(jsonPath("$.firstName").value(updatedAuthorCreateDTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(updatedAuthorCreateDTO.getLastName()));
    }

    @Test
    void shouldDeleteAuthor() throws Exception {
        Author author = authors.get(0);
        mockMvc.perform(delete("/api/author/{id}", author.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetAllAuthors() throws Exception {
        List<AuthorCreateDTO> authorCreateDTOs = authors.stream()
                .map(author -> authorMapper.toAuthorCreateDTO(author))
                .toList();

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(authorCreateDTOs.size())))
                .andExpect(jsonPath("$[*].firstName", containsInAnyOrder(authorCreateDTOs.stream().map(AuthorCreateDTO::getFirstName).toArray())))
                .andExpect(jsonPath("$[*].lastName", containsInAnyOrder(authorCreateDTOs.stream().map(AuthorCreateDTO::getLastName).toArray())));
    }
}