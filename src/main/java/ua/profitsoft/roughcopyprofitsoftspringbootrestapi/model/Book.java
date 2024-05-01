package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book", indexes = {@Index(
        name = "unique_book_title_year",
        columnList = "title, yearPublished", unique = true)})
public class Book {
    public Book(String title, Author author, Integer yearPublished, Set<String> genres) {
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.genres = genres;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    private Integer yearPublished;

    @ElementCollection
    private Set<String> genres = new HashSet<>();
}
