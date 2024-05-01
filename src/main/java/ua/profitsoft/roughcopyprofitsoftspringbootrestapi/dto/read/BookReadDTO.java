package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Data Transfer Object (DTO) representing details of a book.
 * Contains information about the book and its associated author.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookReadDTO {

    @Schema(description = "Id", example = "1")
    public Integer id;

    @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters long.")
    @NotNull
    @Schema(description = "Title", example = "It")
    public String title;

    @NotNull
    public AuthorCreateDTO authorCreateDTO;

    @Digits(integer = 4, fraction = 0, message = "Invalid format. Only digits are allowed.")
    @Positive(message = "Year published must be a positive number.")
    @NotNull
    @Schema(description = "Year published", example = "1986")
    public Integer yearPublished;

    @NotNull
    @Schema(description = "Genres", example = "[\"Horror\", \"Thriller\", \"Dark fantasy\"]")
    public Set<String> genres = new HashSet<>();
}
