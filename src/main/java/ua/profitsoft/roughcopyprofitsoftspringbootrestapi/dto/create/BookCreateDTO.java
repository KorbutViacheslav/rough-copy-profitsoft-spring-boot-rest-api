package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 24.04.2024
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDTO {
    @Size(min = 1, max = 32, message = "Title must be between 1 and 32 characters long.")
    @NotNull
    @Schema(description = "Title", example = "It")
    public String title;

    @Digits(integer = 4, fraction = 0, message = "Invalid format. Only digits are allowed.")
    @Positive(message = "Year published must be a positive number.")
    @NotNull
    @Schema(description = "Year published", example = "1986")
    public Integer yearPublished;

    @NotNull
    private AuthorCreateDTO author;

    @NotNull
    @ElementCollection
    @Schema(description = "Genres", example = "[\"Horror\", \"Thriller\", \"Dark fantasy\"]")
    public Set<String> genres = new HashSet<>();
}
