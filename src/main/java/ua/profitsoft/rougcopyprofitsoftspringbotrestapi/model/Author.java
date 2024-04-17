package ua.profitsoft.rougcopyprofitsoftspringbotrestapi.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@Entity
public class Author {
    @Id
    private Integer id;

    private String firstName;

    private String lastName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private List<Book> bookList;
}
