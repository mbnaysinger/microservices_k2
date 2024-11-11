package com.naysinger.product.api.rest;

import com.naysinger.product.infrastructure.jpa.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@Service
public class SaveAuthorTestService {
    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Product saveAuthorWithId(Long id, String name, Long version) {
        Product savedAuthor = new Product();
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
