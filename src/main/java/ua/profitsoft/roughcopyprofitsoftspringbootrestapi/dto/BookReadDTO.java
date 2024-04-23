package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto;

import jakarta.validation.constraints.*;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public class BookReadDTO {

    public Integer id;

    @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters long.")
    @NotNull
    public String title;

    @NotNull
    public Author author;

    @Digits(integer = 4, fraction = 0, message = "Invalid format. Only digits are allowed.")
    @Positive(message = "Year published must be a positive number.")
    @NotNull
    public Integer yearPublished;

    @NotNull
    public Set<String> genres = new HashSet<>();
}
