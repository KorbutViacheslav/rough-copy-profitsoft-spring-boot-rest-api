package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.web.filter;

import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 27.04.2024
 */

/**
 * This class represents a specification for filtering books based on criteria provided in a BookFilterRequest.
 * It implements the Specification<Book> interface, which allows it to be used in Spring Data JPA repositories
 * for dynamic querying. The toPredicate method converts the filter request into a JPA predicate to be used in
 * querying books from the database.
 */
@AllArgsConstructor
public class BookSpecification implements Specification<Book> {
    /**
     * The filter request containing the criteria for filtering.
     */
    private BookFilterRequest filterRequest;

    /**
     * Converts the filter request into a JPA predicate to be used for filtering books.
     *
     * @param root            The root entity in the query.
     * @param query           The criteria query to be built.
     * @param criteriaBuilder The criteria builder to construct the query predicates.
     * @return A predicate representing the filtering criteria for books.
     */
    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (filterRequest.getTitle() != null && !filterRequest.getTitle().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("title"), filterRequest.getTitle()));
        }

        if (filterRequest.getYearPublish() != null) {
            predicates.add(criteriaBuilder.equal(root.get("yearPublished"), filterRequest.getYearPublish()));
        }

        if (filterRequest.getAuthorFirstName() != null && !filterRequest.getAuthorFirstName().isEmpty()) {
            Join<Book, Author> authorJoin = root.join("author");
            predicates.add(criteriaBuilder.equal(authorJoin.get("firstName"), filterRequest.getAuthorFirstName()));
        }

        if (filterRequest.getAuthorLastName() != null && !filterRequest.getAuthorLastName().isEmpty()) {
            Join<Book, Author> authorJoin = root.join("author");
            predicates.add(criteriaBuilder.equal(authorJoin.get("lastName"), filterRequest.getAuthorLastName()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
