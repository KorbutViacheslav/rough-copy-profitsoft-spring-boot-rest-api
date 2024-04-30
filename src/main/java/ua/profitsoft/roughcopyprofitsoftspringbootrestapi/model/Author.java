package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author", indexes = {@Index(
                name = "unique_author_name",
                columnList = "firstName, lastName", unique = true)})
public class Author {
    public Author(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Book> bookList = new HashSet<>();
}
