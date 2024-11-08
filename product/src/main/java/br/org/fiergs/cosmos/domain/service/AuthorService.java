package br.org.fiergs.cosmos.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.fiergs.cosmos.domain.model.Author;
import br.org.fiergs.cosmos.domain.port.AuthorPort;

@Service
public class AuthorService {

    private final AuthorPort authorPort;

    @Autowired
    public AuthorService(AuthorPort authorPort) {
        this.authorPort = authorPort;
    }

    @Transactional(readOnly = true)
    public Optional<Author> getAuthorById(Long id) {
        return authorPort.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Author> getAllAuthors(int page, int size) {
        return authorPort.findAll(page, size);
    }

    @Transactional
    public Author createAuthor(Author author) {
        return authorPort.save(author);
    }

    @Transactional
    public Optional<Author> updateAuthor(Author authorParam) {
        return authorPort.findById(authorParam.getId())
                .map(param -> authorPort.save(Author.builder(param)
                        .name(authorParam.getName())
                        .version(authorParam.getVersion())
                        .build()));
    }

    @Transactional
    public Optional<Author> deleteAuthor(Long id) {
        return authorPort.findById(id)
                .map(authorToDelete -> {
                    authorPort.delete(authorToDelete);
                    return authorToDelete;
                });
    }
}
