package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Viacheslav Korbut
 * Date: 24.04.2024
 */

/**
 * Data Transfer Object (DTO) for creating a new author.
 * Represents the basic information required to create an author entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreateDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z.]+$", message = "Invalid author name format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    @Schema(description = "Author name", example = "Stephen")
    public String firstName;


    @Pattern(regexp = "^[A-Z][a-zA-Z.]+$", message = "Invalid author lastname format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    @Schema(description = "Author lastname", example = "King")
    public String lastName;
}
