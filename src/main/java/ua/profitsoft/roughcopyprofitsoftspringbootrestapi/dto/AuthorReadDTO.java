package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 22.04.2024
 */
public class AuthorReadDTO {

    public Integer id;

    @Pattern(regexp = "^[a-z]+$", message = "Invalid author name format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    public String firstName;

    @Pattern(regexp = "^[a-z]+$", message = "Invalid author lastname format")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @NotNull
    public String lastName;

    public Set<BookReadDTO> bookList = new HashSet<>();
}
