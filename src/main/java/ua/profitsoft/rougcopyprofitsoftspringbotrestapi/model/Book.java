package ua.profitsoft.rougcopyprofitsoftspringbotrestapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    private Integer yearPublished;

    @ElementCollection
    private List<String> genres;

}
