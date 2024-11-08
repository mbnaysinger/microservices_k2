package br.org.fiergs.cosmos.infrastructure.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.domain.port.AuthorPort;
import br.org.fiergs.cosmos.infrastructure.jpa.converter.AuthorEntityConverter;
import br.org.fiergs.cosmos.infrastructure.jpa.entity.AuthorEntity;
import br.org.fiergs.cosmos.infrastructure.jpa.repository.JpaAuthorRepository;

@Component
public class AuthorAdapter implements AuthorPort {

    private final JpaAuthorRepository jpaAuthorRepository;
    private final AuthorEntityConverter authorEntityConverter;

    @Autowired
    public AuthorAdapter(JpaAuthorRepository jpaAuthorRepository,
            AuthorEntityConverter authorEntityConverter) {
        this.jpaAuthorRepository = jpaAuthorRepository;
        this.authorEntityConverter = authorEntityConverter;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return jpaAuthorRepository.findById(id)
                .map(authorEntityConverter::toDomainModel);
    }

    @Override
    public List<Author> findAll(int page, int size) {
        return jpaAuthorRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(authorEntityConverter::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public Author save(Author author) {
        AuthorEntity entity = authorEntityConverter.toEntity(author);
        return authorEntityConverter.toDomainModel(jpaAuthorRepository.saveAndFlush(entity));
    }

    @Override
    public void delete(Author author) {
        AuthorEntity entity = authorEntityConverter.toEntity(author);
        entity.setDeleted(true);
        jpaAuthorRepository.saveAndFlush(entity);
    }
}
