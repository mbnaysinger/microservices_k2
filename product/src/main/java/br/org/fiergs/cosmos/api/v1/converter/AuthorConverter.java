package br.org.fiergs.cosmos.api.v1.converter;

import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.api.v1.dto.AuthorCreateDto;
import br.org.fiergs.cosmos.api.v1.dto.AuthorDto;
import br.org.fiergs.cosmos.api.v1.dto.AuthorUpdateDto;
import br.org.fiergs.cosmos.domain.model.Author;

@Component
public class AuthorConverter {

    public Author toDomainModel(Long id, AuthorUpdateDto dto) {
        return Author.builder()
                .id(id)
                .name(dto.getName())
                .version(dto.getVersion())
                .build();
    }

    public Author toDomainModel(AuthorCreateDto dto) {
        return Author.builder()
                .name(dto.getName())
                .version(0L)
                .build();
    }

    public AuthorDto toDto(Author author) {
        AuthorDto dto = new AuthorDto();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setCreatedAt(author.getCreatedAt());
        dto.setUpdatedAt(author.getUpdatedAt());
        dto.setCreatedBy(author.getCreatedBy());
        dto.setUpdatedBy(author.getUpdatedBy());
        dto.setVersion(author.getVersion());
        return dto;
    }
}
