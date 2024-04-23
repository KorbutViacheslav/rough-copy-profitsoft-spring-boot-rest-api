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
public class Book {
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
/**
 * @JsonManagedReference та @JsonBackReference - це анотації, які допомагають Jackson управляти серіалізацією об'єктів з циклічними зв'язками.
 *
 *     @JsonManagedReference:
 *         Використовується на полі батьківського об'єкта (у нашому випадку, в класі Author), яке посилається на дочірні об'єкти (в класі Book).
 *         Каже Jackson, що це поле відповідає за "кероване" (managed) значення, тобто за додавання та видалення об'єктів у зв'язку.
 *         При серіалізації Jackson включає це поле, але ігнорує будь-які вказівки зворотнього посилання (@JsonBackReference), щоб уникнути циклічної залежності.
 *
 *     @JsonBackReference:
 *         Використовується на полі дочірнього об'єкта (у нашому випадку, в класі Book), яке посилається на батьківський об'єкт (в класі Author).
 *         Каже Jackson, що це поле є "зворотнім" (back) посиланням на батьківський об'єкт, але не керує його життєвим циклом.
 *         При серіалізації Jackson ігнорує це поле, але використовує його для десеріалізації об'єктів, які посилаються на батьківський об'єкт.
 *
 * Загалом, ці анотації допомагають управляти серіалізацією об'єктів з циклічними зв'язками, уникнувши безкінечної рекурсії та переповнення стеку пам'яті.
 */
}
