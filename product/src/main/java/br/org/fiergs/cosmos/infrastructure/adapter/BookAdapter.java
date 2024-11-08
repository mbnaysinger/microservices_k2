package br.org.fiergs.cosmos.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.domain.model.Book;
import br.org.fiergs.cosmos.domain.port.BookPort;
import br.org.fiergs.cosmos.infrastructure.jpa.converter.BookEntityConverter;
import br.org.fiergs.cosmos.infrastructure.jpa.entity.BookEntity;
import br.org.fiergs.cosmos.infrastructure.jpa.repository.JpaBookRepository;

@Component
public class BookAdapter implements BookPort {

    private final JpaBookRepository jpaBookRepository;
    private final BookEntityConverter bookEntityConverter;

    @Autowired
    public BookAdapter(JpaBookRepository jpaBookRepository, BookEntityConverter bookEntityConverter) {
        this.jpaBookRepository = jpaBookRepository;
        this.bookEntityConverter = bookEntityConverter;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return jpaBookRepository.findById(id).map(bookEntityConverter::toDomainModel);
    }

    @Override
    public List<Book> findAll(int page, int size) {
        return jpaBookRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(bookEntityConverter::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Book save(Book book) {
        BookEntity entity = bookEntityConverter.toEntity(book);
        return bookEntityConverter.toDomainModel(jpaBookRepository.saveAndFlush(entity));
    }

    @Override
    public void delete(Book book) {
        BookEntity entity = bookEntityConverter.toEntity(book);
        entity.setDeleted(true);
        jpaBookRepository.saveAndFlush(entity);
    }
}
