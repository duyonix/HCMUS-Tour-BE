package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.Costume;
import com.onix.hcmustour.model.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CostumeRepository extends JpaRepository<Costume, Long> {
    Optional<Costume> findByName(String name);

    @Query(value = """
        SELECT s FROM Costume s\s
        WHERE s.name LIKE %:name% AND\s
        (:scopeId IS NULL OR s.scope.id = :scopeId)\s
        """)
    Page<Costume> findByNameContainingAndScopeId(@Param("name") String name, @Param("scopeId") Integer scopeId, Pageable pageable);
}
