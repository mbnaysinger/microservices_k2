package br.org.fiergs.cosmos.infrastructure.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.org.fiergs.cosmos.infrastructure.jpa.entity.BookEntity;

@Repository
public interface JpaBookRepository extends JpaRepository<BookEntity, Long> {

    @Override
    @Query("SELECT b FROM BookEntity b WHERE b.deleted = false AND b.id = :id")
    Optional<BookEntity> findById(@Param("id") Long id);

    @Override
    @Query("SELECT b FROM BookEntity b WHERE b.deleted = false")
    List<BookEntity> findAll();

    @Override
    @Query("SELECT b FROM BookEntity b WHERE b.deleted = false")
    Page<BookEntity> findAll(Pageable pageable);
}
