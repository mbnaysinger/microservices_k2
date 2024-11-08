package br.org.fiergs.cosmos.infrastructure.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.fiergs.cosmos.infrastructure.jpa.entity.AuthorEntity;

@Repository
public interface JpaAuthorRepository extends JpaRepository<AuthorEntity, Long> {

    @Override
    @Query("SELECT a FROM AuthorEntity a WHERE a.deleted = false AND a.id = :id")
    Optional<AuthorEntity> findById(@Param("id") Long id);

    @Override
    @Query("SELECT a FROM AuthorEntity a WHERE a.deleted = false")
    List<AuthorEntity> findAll();

    @Override
    @Query("SELECT a FROM AuthorEntity a WHERE a.deleted = false")
    Page<AuthorEntity> findAll(Pageable pageable);
}
