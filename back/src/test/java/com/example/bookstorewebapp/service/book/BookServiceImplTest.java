package com.example.bookstorewebapp.service.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.bookstorewebapp.dto.book.BookDto;
import com.example.bookstorewebapp.dto.book.BookSearchParameters;
import com.example.bookstorewebapp.dto.book.CreateBookRequestDto;
import com.example.bookstorewebapp.exception.EntityNotFoundException;
import com.example.bookstorewebapp.mapper.BookMapper;
import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.repository.book.BookRepository;
import com.example.bookstorewebapp.repository.book.BookSpecificationBuilder;
import com.example.bookstorewebapp.service.book.impl.BookServiceImpl;
import com.example.bookstorewebapp.service.category.CategoryService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private BookSpecificationBuilder specificationBuilder;

    @InjectMocks
    private BookServiceImpl bookService;
    private final Long bookId = 1L;

    @Test
    @DisplayName("Create Book successfully")
    void createBook_Success() {
        CreateBookRequestDto requestDto = createRequestDto();
        Book book = createBook();
        BookDto responseDto = createResponseDto();

        doNothing().when(categoryService).isAllCategoriesPresent(requestDto.getCategoryIds());
        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        BookDto actual = bookService.create(requestDto);

        verify(bookRepository).save(book);
        assertEquals(actual, responseDto);
    }

    @Test
    @DisplayName("Update Book by ID throws EntityNotFoundException when book does not exist")
    void updateBookById_ThrowsEntityNotFoundException_WhenBookNotExist() {
        CreateBookRequestDto requestDto = createRequestDto();
        requestDto.setCategoryIds(Set.of(1L, 2L));

        when(bookRepository.existsById(bookId)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateById(bookId, requestDto);
        });
        assertEquals("There is no book with id: " + bookId, exception.getMessage());
    }

    @Test
    @DisplayName("Find Book by ID returns book")
    void findBookById_ReturnsBook() {
        Book book = createBook();
        book.setId(bookId);
        BookDto responseDto = createResponseDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        BookDto actual = bookService.findById(bookId);

        verify(bookRepository).findById(bookId);
        assertEquals(actual, responseDto);
    }

    @Test
    @DisplayName("Find Book by ID throws EntityNotFoundException when book does not exist")
    void findBookById_ThrowsEntityNotFoundException_WhenBookNotExist() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.findById(bookId);
        });
    }

    @Test
    @DisplayName("Delete Book by ID successfully")
    void deleteBookById_Success() {
        when(bookRepository.existsById(bookId)).thenReturn(true);

        bookService.deleteById(bookId);

        verify(bookRepository).deleteById(bookId);
    }

    @Test
    @DisplayName("Delete Book by ID throws EntityNotFoundException when book does not exist")
    void deleteBookById_ThrowsEntityNotFoundException_WhenBookNotExist() {
        when(bookRepository.existsById(bookId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteById(bookId);
        });
    }

    @Test
    @DisplayName("Find all Books returns books")
    void findAllBooks_ReturnsBooks() {
        Pageable pageable = Pageable.unpaged();
        Book book = createBook();
        BookDto responseDto = createResponseDto();

        when(bookRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        List<BookDto> actual = bookService.findAll(pageable);

        verify(bookRepository).findAll(pageable);
        assertEquals(List.of(responseDto), actual);
    }

    @Test
    @DisplayName("Search Books returns books")
    void searchBooks_ReturnsBooks() {
        Pageable pageable = Pageable.unpaged();
        BookSearchParameters params = new BookSearchParameters(
                new String[]{"Title1"}, new String[]{"Author1"});
        Book book = createBook();
        BookDto responseDto = createResponseDto();

        when(specificationBuilder.build(params)).thenReturn((Specification<Book>)
                (root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        when(bookRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(book)));
        when(bookMapper.toDto(book)).thenReturn(responseDto);

        List<BookDto> actual = bookService.search(params, pageable);

        verify(bookRepository)
                .findAll(any(Specification.class), any(Pageable.class));
        assertEquals(List.of(responseDto), actual);
    }

    private CreateBookRequestDto createRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setIsbn("ISBN 978-1-721-11223-7");
        requestDto.setTitle("Title1");
        requestDto.setAuthor("Author1");
        requestDto.setIsbn("ISBN 978-1-721-11223-7");
        requestDto.setPrice(BigDecimal.TEN);
        requestDto.setDescription("Description1");
        requestDto.setCategoryIds(Set.of(1L));
        return requestDto;
    }

    private Book createBook() {
        Book book = new Book();
        book.setIsbn("ISBN 978-1-721-11223-7");
        book.setTitle("Title1");
        book.setAuthor("Author1");
        book.setIsbn("ISBN 978-1-721-11223-7");
        book.setPrice(BigDecimal.TEN);
        book.setDescription("Description1");
        return book;
    }

    private BookDto createResponseDto() {
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Title1");
        bookDto.setAuthor("Author1");
        bookDto.setIsbn("ISBN 978-1-721-11223-7");
        bookDto.setPrice(BigDecimal.TEN);
        bookDto.setDescription("Description1");
        bookDto.setCategoryIds(Set.of(1L));
        return bookDto;
    }
}
