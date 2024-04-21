package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.impl;

import org.springframework.stereotype.Service;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.repository.AuthorRepository;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.service.AuthorService;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceNotFoundException;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 20.04.2024
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Author getAuthorById(Integer id) {
        return authorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public Author updateAuthor(Author author) {
        return authorRepository.findById(author.getId())
                .map(entity -> {
                    entity.setFirstName(author.getFirstName());
                    entity.setLastName(author.getLastName());
                    entity.setBookList(author.getBookList());
                    return authorRepository.save(entity);
                })
                .orElseThrow(ResourceNotFoundException::new);
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
}
