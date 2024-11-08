package br.org.fiergs.cosmos.api.v1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookUpdateDto {

    @NotBlank
    private String title;

    @NotBlank
    private String isbn;

    @NotNull
    private LocalDate publishedDate;

    @NotNull
    private Long authorId;

    @NotNull
    private Long version;

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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BookCreateDto [title=" + title + ", isbn=" + isbn + ", publishedDate=" + publishedDate + ", authorId="
                + authorId + ", version=" + version + "]";
    }

}
