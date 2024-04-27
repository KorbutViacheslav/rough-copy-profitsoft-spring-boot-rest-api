package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 22.04.2024
 */
public class AuthorReadDTO {

    @Schema(description = "Author id", example = "1")
    public Integer id;

    @Pattern(regexp = "^[A-Z][a-zA-Z.]+$", message = "Invalid author name format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    @Schema(description = "Author name", example = "Stephen")
    public String firstName;

    @Pattern(regexp = "^[A-Z][a-z.]+$", message = "Invalid author lastname format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    @Schema(description = "Author lastname", example = "King")
    public String lastName;

    public Set<BookCreateDTO> bookList = new HashSet<>();
}
