package com.onix.hcmustour.service;

import com.onix.hcmustour.dto.mapper.UserMapper;
import com.onix.hcmustour.dto.model.UserDto;
import com.onix.hcmustour.exception.ApplicationException;
import com.onix.hcmustour.exception.EntityType;
import com.onix.hcmustour.exception.ExceptionType;
import com.onix.hcmustour.model.User;
import com.onix.hcmustour.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<UserDto> getUsers(Integer page, Integer size, String search) {
        log.info("UserService::getUsers execution started");
        Page<UserDto> userDtos;
        try {
            log.debug("UserService::getUsers request parameters page {}, size {}, search {}", page, size, search);
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            Page<User> users = userRepository.findByEmailContaining(search, pageable);

            userDtos = users.map(UserMapper::toUserDto);
            log.debug("UserService::getUsers received response from database {}", userDtos);
        } catch (Exception e) {
            log.error("UserService::getUsers execution failed with error {}", e.getMessage());
            throw exception(EntityType.USER, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("UserService::getUsers execution completed");
        return userDtos;
    }

    public UserDto getUser(Integer id) {
        log.info("UserService::getUser execution started");
        UserDto userDto;

        log.debug("UserService::getUser request parameters id {}", id);
        User user = userRepository.findById(id.longValue())
                .orElseThrow(() -> {
                   log.error("UserService::getUser execution failed with user not found {}", id);
                     return exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        userDto = UserMapper.toUserDto(user);
        log.debug("UserService::getUser received response from database {}", userDto);

        log.info("UserService::getUser execution completed");
        return userDto;
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }
}
