package br.org.fiergs.cosmos.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.fiergs.common.model.exception.BusinessException;
import br.org.fiergs.cosmos.domain.model.Book;
import br.org.fiergs.cosmos.domain.port.BookPort;

@Service
public class BookService {

    private final BookPort bookPort;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookPort bookPort, AuthorService authorService) {
        this.bookPort = bookPort;
        this.authorService = authorService;
    }

    @Transactional(readOnly = true)
    public Optional<Book> getBookById(Long id) {
        return bookPort.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks(int page, int size) {
        return bookPort.findAll(page, size);
    }

    @Transactional
    public Book createBook(Book book) {
        if (isIsbnDuplicate(book.getIsbn())) {
            throw new BusinessException("A book with ISBN " + book.getIsbn() + " already exists.");
        }

        return bookPort.save(Book.builder(book)
                .author(authorService.getAuthorById(book.getAuthorId())
                        .orElseThrow(() -> new BusinessException(
                                "Author not found with id: " + book.getAuthorId())))
                .build());
    }

    @Transactional
    public Optional<Book> updateBook(Book bookParam) {
        return bookPort.findById(bookParam.getId())
                .map(book -> bookPort.save(Book.builder(book)
                        .title(bookParam.getTitle())
                        .isbn(bookParam.getIsbn())
                        .publishedDate(bookParam.getPublishedDate())
                        .author(authorService.getAuthorById(bookParam.getAuthorId())
                                .orElseThrow(() -> new BusinessException(
                                        "Author not found with id: " + bookParam.getAuthorId())))
                        .version(bookParam.getVersion())
                        .build()));
    }

    @Transactional
    public Optional<Book> deleteBook(Long id) {
        return bookPort.findById(id)
                .map(bookToDelete -> {
                    bookPort.delete(Book.builder(bookToDelete)
                            .author(authorService.getAuthorById(bookToDelete.getAuthorId()).get())
                            .build());
                    return bookToDelete;
                });
    }

    private boolean isIsbnDuplicate(String isbn) {
        return false;
    }
}
