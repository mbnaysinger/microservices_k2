package br.org.fiergs.cosmos.infrastructure.jpa.converter;

import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.domain.model.Book;
import br.org.fiergs.cosmos.infrastructure.jpa.entity.BookEntity;

@Component
public class BookEntityConverter {

    public final AuthorEntityConverter authorEntityConverter;

    public BookEntityConverter(AuthorEntityConverter authorEntityConverter) {
        this.authorEntityConverter = authorEntityConverter;
    }

    public Book toDomainModel(BookEntity entity) {
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .publishedDate(entity.getPublishedDate())
                .author(Author.builder()
                        .id(entity.getAuthor().getId())
                        .name(entity.getAuthor().getName())
                        .build())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedBy(entity.getUpdatedBy())
                .version(entity.getVersion())
                .build();
    }

    public BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.getId());
        entity.setTitle(book.getTitle());
        entity.setIsbn(book.getIsbn());
        entity.setPublishedDate(book.getPublishedDate());
        entity.setAuthor(authorEntityConverter.toEntity((book.getAuthor())));
        entity.setCreatedAt(book.getCreatedAt());
        entity.setUpdatedAt(book.getUpdatedAt());
        entity.setCreatedBy(book.getCreatedBy());
        entity.setUpdatedBy(book.getUpdatedBy());
        entity.setVersion(book.getVersion());
        return entity;
    }

}
