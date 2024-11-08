package br.org.fiergs.cosmos.domain.port;

import java.util.List;
import java.util.Optional;

import br.org.fiergs.cosmos.domain.model.Book;

public interface BookPort {
    Optional<Book> findById(Long id);

    List<Book> findAll(int page, int size);

    Book save(Book book);

    void delete(Book book);
}
