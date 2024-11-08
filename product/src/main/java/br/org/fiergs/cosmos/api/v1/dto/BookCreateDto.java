package br.org.fiergs.cosmos.api.v1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookCreateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotNull
    private LocalDate publishedDate;

    @NotNull
    private Long authorId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return "BookCreateDto [title=" + title + ", isbn=" + isbn + ", publishedDate=" + publishedDate + ", authorId="
                + authorId + "]";
    }

}
