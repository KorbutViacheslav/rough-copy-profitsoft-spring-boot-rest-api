package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.dto;

import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.model.Author;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
public class BookReadDTO {
    public Integer id;

    public String title;

    public Author author;

    public Integer yearPublished;

    public Set<String> genres = new HashSet<>();
}
