package com.example.bookstorewebapp.repository.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.bookstorewebapp.model.Book;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Sql(scripts = "/sql/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(scripts = "/sql/set-categories.sql")
    @DisplayName("Find all books by category id")
    void findAllByCategoryId_Id1_ReturnsNotEmptyList() {
        List<Book> actual = bookRepository.findAllByCategoryId(1L, Pageable.unpaged());
        assertEquals(1, actual.size());
        assertEquals(11.11, actual.get(0).getPrice().doubleValue());
    }

    @Test
    @Sql(scripts = "/sql/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findAll() {
        List<Book> books = bookRepository.findAll(Pageable.unpaged()).getContent();
        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
        assertEquals(11.11, books.get(0).getPrice().doubleValue());
    }

    @Test
    @Sql(scripts = "/sql/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/teardown.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void findById() {
        Book foundBook = bookRepository.findById(3L).orElse(null);
        assertNotNull(foundBook);
        assertEquals(3, foundBook.getId());
    }
}
