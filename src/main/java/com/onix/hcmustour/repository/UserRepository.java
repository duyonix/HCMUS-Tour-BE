package com.onix.hcmustour.repository;

import com.onix.hcmustour.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
