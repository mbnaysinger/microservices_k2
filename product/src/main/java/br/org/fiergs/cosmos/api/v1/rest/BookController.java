package br.org.fiergs.cosmos.api.v1.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.org.fiergs.common.model.exception.ResourceNotFoundException;
import br.org.fiergs.cosmos.api.v1.converter.BookConverter;
import br.org.fiergs.cosmos.api.v1.dto.BookCreateDto;
import br.org.fiergs.cosmos.api.v1.dto.BookDto;
import br.org.fiergs.cosmos.api.v1.dto.BookUpdateDto;
import br.org.fiergs.cosmos.domain.model.Book;
import br.org.fiergs.cosmos.domain.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;
    private final BookConverter bookConverter;

    @Autowired
    public BookController(BookService bookService, BookConverter bookConverter) {
        this.bookService = bookService;
        this.bookConverter = bookConverter;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return ResponseEntity.ok(bookConverter.toDto(book));
    }

    @GetMapping
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<List<BookDto>> getAllBooks(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Book> books = bookService.getAllBooks(page, size);
        List<BookDto> dtos = books.stream().map(bookConverter::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto) {
        Book book = bookConverter.toDomainModel(bookCreateDto);
        book = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookConverter.toDto(book));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        Book book = bookConverter.toDomainModel(id, bookUpdateDto);
        book = bookService.updateBook(book)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return ResponseEntity.ok(bookConverter.toDto(book));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return ResponseEntity.noContent().build();
    }
}
