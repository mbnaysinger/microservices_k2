package br.org.fiergs.cosmos.domain.port;

import java.util.List;
import java.util.Optional;

import br.org.fiergs.cosmos.domain.model.Author;

public interface AuthorPort {
    Optional<Author> findById(Long id);

    List<Author> findAll(int page, int size);

    Author save(Author author);

    void delete(Author author);
}
