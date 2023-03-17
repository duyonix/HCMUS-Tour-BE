package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.Costume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostumeRepository extends JpaRepository<Costume, Long> {
    Optional<Costume> findByName(String name);
}
