package br.org.fiergs.cosmos.api.v1.converter;

import org.springframework.stereotype.Component;

import br.org.fiergs.cosmos.api.v1.dto.BookCreateDto;
import br.org.fiergs.cosmos.api.v1.dto.BookDto;
import br.org.fiergs.cosmos.api.v1.dto.BookUpdateDto;
import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.domain.model.Book;

@Component
public class BookConverter {

    public Book toDomainModel(Long id, BookUpdateDto dto) {
        return Book.builder()
                .id(id)
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publishedDate(dto.getPublishedDate())
                .author(Author.builder()
                        .id(dto.getAuthorId())
                        .build())
                .version(dto.getVersion())
                .build();
    }

    public Book toDomainModel(BookCreateDto dto) {
        return Book.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .publishedDate(dto.getPublishedDate())
                .author(Author.builder()
                        .id(dto.getAuthorId())
                        .build())
                .version(0L)
                .build();
    }

    public BookDto toDto(Book book) {
        BookDto dto = new BookDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setAuthorId(book.getAuthor().getId());
        dto.setCreatedAt(book.getCreatedAt());
        dto.setUpdatedAt(book.getUpdatedAt());
        dto.setCreatedBy(book.getCreatedBy());
        dto.setUpdatedBy(book.getUpdatedBy());
        dto.setVersion(book.getVersion());
        return dto;
    }
}
