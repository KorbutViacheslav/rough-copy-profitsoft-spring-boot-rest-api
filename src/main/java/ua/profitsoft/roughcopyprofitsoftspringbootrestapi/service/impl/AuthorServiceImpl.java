package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceNotFoundException;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 20.04.2024
 */
@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourceIsExistException();
        }
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Author updateAuthor(Integer id, Author author) {
        Author a = getAuthorById(id);
        a.setFirstName(author.getFirstName());
        a.setLastName(author.getLastName());
        return authorRepository.save(a);
    }

    @Override
    public boolean deleteAuthorById(Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        } else {
            throw new ResourceNotFoundException();
        }

    }

    @Override
    public List<Author> getAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Author findByFirstNameAndLastName(String firstName, String lastName) {
        return authorRepository.findByFirstNameAndLastName(firstName, lastName)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Page<Author> findAllAuthor() {
        return null;
    }
}
