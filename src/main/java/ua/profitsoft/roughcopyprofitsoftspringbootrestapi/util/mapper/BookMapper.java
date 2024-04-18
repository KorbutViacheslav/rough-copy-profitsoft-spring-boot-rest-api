package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto.BookReadDTO;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Book;

import java.util.List;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@Mapper(componentModel = "spring")
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    BookReadDTO toBookReadDTO(Book book);

    @Mapping(target = "id", ignore = true)
    Book toBook(BookReadDTO bookReadDTO);

    List<BookReadDTO> toBookReadDTOList(List<Book> books);
}
