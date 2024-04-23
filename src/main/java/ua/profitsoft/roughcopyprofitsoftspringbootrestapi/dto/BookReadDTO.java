package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @Pattern(regexp = "^[0-9]+$", message = "Invalid format. Only digits are allowed.")
    @Size(min = 1, max = 4, message = "Year published must be between 1 and 4 digits long.")
    @NotNull
    public Integer yearPublished;

    @NotNull
    public Set<String> genres = new HashSet<>();
}
