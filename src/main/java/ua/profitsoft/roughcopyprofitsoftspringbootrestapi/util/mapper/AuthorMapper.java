package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.AuthorReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

/**
 * Author: Viacheslav Korbut
 * Date: 23.04.2024
 */
@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorReadDTO toAuthorReadDTO(Author author);

    @Mapping(target = "bookList", ignore = true)
    Author toAuthor(AuthorReadDTO authorReadDTO);
}
