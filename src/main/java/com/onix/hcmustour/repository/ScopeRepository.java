package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
    Optional<Scope> findByName(String name);
}
