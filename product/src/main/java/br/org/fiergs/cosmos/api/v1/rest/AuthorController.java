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
import br.org.fiergs.cosmos.api.v1.converter.AuthorConverter;
import br.org.fiergs.cosmos.api.v1.dto.AuthorCreateDto;
import br.org.fiergs.cosmos.api.v1.dto.AuthorDto;
import br.org.fiergs.cosmos.api.v1.dto.AuthorUpdateDto;
import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.domain.service.AuthorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorConverter authorConverter;

    @Autowired
    public AuthorController(AuthorService authorService, AuthorConverter authorConverter) {
        this.authorService = authorService;
        this.authorConverter = authorConverter;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<AuthorDto> getAuthorById(@PathVariable Long id) {
        Author author = authorService.getAuthorById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return ResponseEntity.ok(authorConverter.toDto(author));
    }

    @GetMapping
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<List<AuthorDto>> getAllAuthors(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<Author> authors = authorService.getAllAuthors(page, size);
        List<AuthorDto> dtos = authors.stream()
                .map(authorConverter::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto) {
        Author author = authorConverter.toDomainModel(authorCreateDto);
        author = authorService.createAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authorConverter.toDto(author));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id,
            @Valid @RequestBody AuthorUpdateDto authorCreateDto) {
        Author authorParam = authorConverter.toDomainModel(id, authorCreateDto);
        Author persistedAuthor = authorService.updateAuthor(authorParam)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return ResponseEntity.ok(authorConverter.toDto(persistedAuthor));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('COSMOS_ADM[*]')")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        return ResponseEntity.noContent().build();
    }
}
