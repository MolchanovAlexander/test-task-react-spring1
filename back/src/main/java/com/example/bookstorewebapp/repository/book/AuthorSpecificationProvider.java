package com.example.bookstorewebapp.repository.book;

import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.repository.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    private static final String AUTHOR = "author";

    @Override
    public String getKey() {
        return AUTHOR;
    }

    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder) -> root.get(AUTHOR)
                .in(Arrays.stream(params).toArray());
    }
}
