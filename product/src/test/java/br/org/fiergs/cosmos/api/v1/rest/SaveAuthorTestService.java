package br.org.fiergs.cosmos.api.v1.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.org.fiergs.cosmos.infrastructure.jpa.entity.AuthorEntity;
import jakarta.persistence.EntityManager;

@Service
public class SaveAuthorTestService {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public AuthorEntity saveAuthorWithId(Long id, String name, Long version) {
        AuthorEntity savedAuthor = new AuthorEntity();
        savedAuthor.setId(1L);
        savedAuthor.setName("Author Name");
        savedAuthor.setVersion(1L);

        entityManager.createNativeQuery(
                "INSERT INTO authors (id, name, version, deleted) VALUES (:id, :name, :version, :deleted)")
                .setParameter("id", id)
                .setParameter("name", name)
                .setParameter("version", version)
                .setParameter("deleted", false)
                .executeUpdate();

        entityManager.flush();

        return savedAuthor;
    }
}
