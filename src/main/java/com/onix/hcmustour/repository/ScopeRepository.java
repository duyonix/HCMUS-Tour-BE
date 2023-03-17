package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
    Optional<Scope> findByName(String name);

    @Query(value = """
        SELECT s FROM Scope s\s
        WHERE s.name LIKE %:name% AND\s
        (:categoryId IS NULL OR s.category.id = :categoryId)\s
        """)
    Page<Scope> findByNameContainingAndCategoryId(@Param("name") String name, @Param("categoryId") Integer categoryId, Pageable pageable);
}
