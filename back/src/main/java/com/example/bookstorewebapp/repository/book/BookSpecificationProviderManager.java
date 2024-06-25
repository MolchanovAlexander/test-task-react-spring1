package com.example.bookstorewebapp.repository.book;

import com.example.bookstorewebapp.model.Book;
import com.example.bookstorewebapp.repository.SpecificationProvider;
import com.example.bookstorewebapp.repository.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager
        implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> bookProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String key) {
        return bookProviders.stream()
                .filter(p -> p.getKey().equals(key))
                .findFirst()
                .orElseThrow(
                        () -> new RuntimeException("Can't find spec provider for key: " + key)
                );
    }
}
