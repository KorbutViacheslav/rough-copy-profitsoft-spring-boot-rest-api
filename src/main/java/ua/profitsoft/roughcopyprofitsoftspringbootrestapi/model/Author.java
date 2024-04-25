package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstName;

    private String lastName;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Book> bookList = new HashSet<>();
}
