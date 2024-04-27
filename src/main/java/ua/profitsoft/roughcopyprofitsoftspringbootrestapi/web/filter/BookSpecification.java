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
@AllArgsConstructor
public class BookSpecification implements Specification<Book> {
    private BookFilterRequest filterRequest;

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
