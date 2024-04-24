package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Author: Viacheslav Korbut
 * Date: 24.04.2024
 */
@Getter
@Setter
public class AuthorCreateDTO {
    @Pattern(regexp = "^[A-Z][a-zA-Z.]+$", message = "Invalid author name format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    public String firstName;


    @Pattern(regexp = "^[A-Z][a-zA-Z.]+$", message = "Invalid author lastname format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    public String lastName;
}
