package br.org.fiergs.cosmos.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class Book {

    private final Long id;
    private final String title;
    private final String isbn;
    private final LocalDate publishedDate;
    private final Author author;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private final Long version;

    private Book(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.isbn = builder.isbn;
        this.publishedDate = builder.publishedDate;
        this.author = builder.author;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.createdBy = builder.createdBy;
        this.updatedBy = builder.updatedBy;
        this.version = builder.version;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public Author getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public Long getVersion() {
        return version;
    }

    public Long getAuthorId() {
        return null == author
                ? null
                : author.getId();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Book book) {
        return new Builder(book);
    }

    public static final class Builder {
        private Long id;
        private String title;
        private String isbn;
        private LocalDate publishedDate;
        private Author author;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private Long version;

        private Builder() {
        }

        private Builder(Book book) {
            this.id = book.id;
            this.title = book.title;
            this.isbn = book.isbn;
            this.publishedDate = book.publishedDate;
            this.author = book.author;
            this.createdAt = book.createdAt;
            this.updatedAt = book.updatedAt;
            this.createdBy = book.createdBy;
            this.updatedBy = book.updatedBy;
            this.version = book.version;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder publishedDate(LocalDate publishedDate) {
            this.publishedDate = publishedDate;
            return this;
        }

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder updatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder createdBy(String createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public Builder updatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
            return this;
        }

        public Builder version(Long version) {
            this.version = version;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    // Getters and equals/hashCode methods
}
