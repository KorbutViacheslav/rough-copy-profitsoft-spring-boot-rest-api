package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.AuthorReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.AuthorCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */

/**
 * Mapper interface responsible for mapping between {@link Author} entity and its corresponding DTOs.
 * Uses MapStruct library for automatic mapping implementation.
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorReadDTO toAuthorReadDTO(Author author);

    @Mapping(target = "bookList", ignore = true)
    Author toAuthor(AuthorReadDTO authorReadDTO);

    AuthorCreateDTO toAuthorCreateDTO(Author author);

    @Mapping(target = "id", ignore = true)
    Author toAuthor(AuthorCreateDTO authorCreateDTO);

    @Mapping(target = "id", ignore = true)
    AuthorReadDTO toAuthorReadDTO(AuthorCreateDTO authorCreateDTO);

    AuthorCreateDTO toAuthorCreateDTO(AuthorReadDTO authorReadDTO);

    List<AuthorCreateDTO> toListAuthorCreateDTO(List<Author> authorList);
}
