package com.example.bookstorewebapp.repository.book;

import com.example.bookstorewebapp.dto.book.BookSearchParameters;
import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.repository.SpecificationBuilder;
import com.example.bookstorewebapp.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book, BookSearchParameters> {
    private static final String TITLE = "title";
    private static final String AUTHOR = "author";
    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParameters searchParameters) {
        Specification<Book> defaultSpec = Specification.where(null);
        if (isNotNullAndNotEmpty(searchParameters.titles())) {
            defaultSpec = getBookSpecification(searchParameters.titles(), TITLE, defaultSpec);
        }
        if (isNotNullAndNotEmpty(searchParameters.authors())) {
            defaultSpec = getBookSpecification(searchParameters.authors(), AUTHOR, defaultSpec);
        }
        return defaultSpec;
    }

    private Specification<Book> getBookSpecification(
            String[] values,
            String parameter,
            Specification<Book> defaultSpec) {
        defaultSpec = defaultSpec
                .and(bookSpecificationProviderManager.getSpecificationProvider(parameter)
                        .getSpecification(values));
        return defaultSpec;
    }

    private static boolean isNotNullAndNotEmpty(String[] searchParameters) {
        return searchParameters != null && searchParameters.length > 0;
    }
}
