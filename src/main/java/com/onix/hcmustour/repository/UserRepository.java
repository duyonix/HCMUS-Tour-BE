package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.Category;
import com.onix.hcmustour.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Page<User> findByEmailContaining(String email, Pageable pageable);
}
