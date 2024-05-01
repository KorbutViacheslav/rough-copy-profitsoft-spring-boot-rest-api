package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.read.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.create.BookCreateDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Mapper interface responsible for mapping between {@link Book} entity and its corresponding DTOs.
 * Uses MapStruct library for automatic mapping implementation.
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(source = "author", target = "authorCreateDTO")
    BookReadDTO toBookReadDTO(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "authorCreateDTO", target = "author")
    Book toBook(BookReadDTO bookReadDTO);

    List<BookReadDTO> toBookReadDTOList(List<Book> books);

    BookCreateDTO toBookCreateDTO(Book book);

    @Mapping(target = "id", ignore = true)
    Book toBook(BookCreateDTO bookCreateDTO);
}
