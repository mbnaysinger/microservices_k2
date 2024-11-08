package br.org.fiergs.cosmos.domain.model;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Author {

    private final Long id;
    private final String name;
    private final Set<Book> books;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String createdBy;
    private final String updatedBy;
    private final Long version;

    private Author(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.books = Collections.unmodifiableSet(new HashSet<>(builder.books));
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
        this.createdBy = builder.createdBy;
        this.updatedBy = builder.updatedBy;
        this.version = builder.version;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Book> getBooks() {
        return books;
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

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(Author author) {
        return new Builder(author);
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Set<Book> books = new HashSet<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String createdBy;
        private String updatedBy;
        private Long version;

        private Builder() {
        }

        private Builder(Author author) {
            this.id = author.id;
            this.name = author.name;
            this.books = new HashSet<>(author.books);
            this.createdAt = author.createdAt;
            this.updatedAt = author.updatedAt;
            this.createdBy = author.createdBy;
            this.updatedBy = author.updatedBy;
            this.version = author.version;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder books(Set<Book> books) {
            this.books = books;
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

        public Author build() {
            return new Author(this);
        }
    }

}
