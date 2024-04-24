package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 24.04.2024
 */
public class BookCreateDTO {
    @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters long.")
    @NotNull
    public String title;

    @Digits(integer = 4, fraction = 0, message = "Invalid format. Only digits are allowed.")
    @Positive(message = "Year published must be a positive number.")
    @NotNull
    public Integer yearPublished;

    private AuthorCreateDTO author;

    @NotNull
    public Set<String> genres = new HashSet<>();
}
