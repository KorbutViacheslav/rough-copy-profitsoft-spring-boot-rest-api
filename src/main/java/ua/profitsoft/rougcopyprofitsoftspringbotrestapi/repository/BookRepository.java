package ua.profitsoft.rougcopyprofitsoftspringbotrestapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.profitsoft.rougcopyprofitsoftspringbotrestapi.model.Book;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
}
