package br.org.fiergs.cosmos.infrastructure.jpa.converter;

import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.infrastructure.jpa.entity.AuthorEntity;

@Component
public class AuthorEntityConverter {

    public Author toDomainModel(AuthorEntity entity) {
        return Author.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();
    }

    public AuthorEntity toEntity(Author author) {
        AuthorEntity entity = new AuthorEntity();
        entity.setId(author.getId());
        entity.setName(author.getName());
        entity.setCreatedAt(author.getCreatedAt());
        entity.setUpdatedAt(author.getUpdatedAt());
        entity.setCreatedBy(author.getCreatedBy());
        entity.setUpdatedBy(author.getUpdatedBy());
        entity.setVersion(author.getVersion());
        return entity;
    }
}
