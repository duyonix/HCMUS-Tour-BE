package com.onix.hcmustour.service;

import com.onix.hcmustour.controller.v1.request.ScopeRequest;
import com.onix.hcmustour.dto.mapper.ScopeMapper;
import com.onix.hcmustour.dto.model.ScopeDto;
import com.onix.hcmustour.exception.ApplicationException;
import com.onix.hcmustour.exception.EntityType;
import com.onix.hcmustour.exception.ExceptionType;
import com.onix.hcmustour.model.Category;
import com.onix.hcmustour.model.Scope;
import com.onix.hcmustour.repository.CategoryRepository;
import com.onix.hcmustour.repository.ScopeRepository;
import com.onix.hcmustour.util.ValueMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ScopeService {
    @Autowired
    private ScopeRepository scopeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ScopeDto addScope(ScopeRequest scopeRequest) {
        log.info("ScopeService::addScope execution started");
        ScopeDto scopeDto;

        scopeRepository.findByName(scopeRequest.getName())
                .ifPresent(scope -> {
                    log.error("ScopeService::addScope execution failed with duplicate scope name {}", scopeRequest.getName());
                    throw exception(EntityType.SCOPE, ExceptionType.DUPLICATE_ENTITY, scopeRequest.getName());
                });

        Category category = categoryRepository.findById(scopeRequest.getCategoryId().longValue())
                .orElseThrow(() -> {
                    log.error("ScopeService::addScope execution failed with invalid category id {}", scopeRequest.getCategoryId());
                    throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, scopeRequest.getCategoryId().toString());
                });

        try {
            Scope newScope = new Scope()
                    .setName(scopeRequest.getName())
                    .setDescription(scopeRequest.getDescription())
                    .setBackgrounds(scopeRequest.getBackgrounds())
                    .setCategory(category);
            log.debug("ScopeService::addScope request parameters {}", ValueMapper.jsonAsString(newScope));

            Scope savedScope = scopeRepository.save(newScope);
            scopeDto = ScopeMapper.toScopeDto(savedScope);
            log.debug("ScopeService::addScope received response from database {}", ValueMapper.jsonAsString(scopeDto));
        } catch (Exception e) {
            log.error("ScopeService::addScope execution failed with error {}", e.getMessage());
            throw exception(EntityType.SCOPE, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("ScopeService::addScope execution completed");
        return scopeDto;
    }

    public Page<ScopeDto> getScopes(Integer page, Integer size, String search, Integer categoryId) {
        log.info("ScopeService::getScopes execution started");
        Page<ScopeDto> scopeDtos;
        try {
            log.debug("ScopeService::getScopes request parameters page {} size {} search {} categoryId {}", page, size, search, categoryId);
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            Page<Scope> scopes = scopeRepository.findByNameContainingAndCategoryId(search, categoryId, pageable);

            scopeDtos = scopes.map(ScopeMapper::toScopeDto);
            log.debug("ScopeService::getScopes received response from database {}", ValueMapper.jsonAsString(scopeDtos));
        } catch (Exception e) {
            log.error("ScopeService::getScopes execution failed with error {}", e.getMessage());
            throw exception(EntityType.SCOPE, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("ScopeService::getScopes execution completed");
        return scopeDtos;
    }

    public ScopeDto getScope(Integer id) {
        log.info("ScopeService::getScope execution started");
        ScopeDto scopeDto;

        log.debug("ScopeService::getScope request parameters id {}", id);
        Scope scope = scopeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("ScopeService::getScope execution failed with invalid scope id {}", id);
                    throw exception(EntityType.SCOPE, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        scopeDto = ScopeMapper.toScopeDto(scope);
        log.debug("ScopeService::getScope received response from database {}", ValueMapper.jsonAsString(scopeDto));

        log.info("ScopeService::getScope execution completed");
        return scopeDto;
    }

    public ScopeDto updateScope(Integer id, ScopeRequest scopeRequest) {
        log.info("ScopeService::updateScope execution started");
        ScopeDto scopeDto;

        log.debug("ScopeService::updateScope request parameters id {} scopeRequest {}", id, ValueMapper.jsonAsString(scopeRequest));
        Scope scope = scopeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("ScopeService::updateScope execution failed with invalid scope id {}", id);
                    throw exception(EntityType.SCOPE, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        Optional<Scope> duplicateScope = scopeRepository.findByName(scopeRequest.getName());
        if (duplicateScope.isPresent() && !duplicateScope.get().getId().equals(scope.getId())) {
            log.error("ScopeService::updateScope execution failed with duplicate scope name {}", scopeRequest.getName());
            throw exception(EntityType.SCOPE, ExceptionType.DUPLICATE_ENTITY, scopeRequest.getName());
        }

        Category category = categoryRepository.findById(scopeRequest.getCategoryId().longValue())
                .orElseThrow(() -> {
                    log.error("ScopeService::updateScope execution failed with invalid category id {}", scopeRequest.getCategoryId());
                    throw exception(EntityType.CATEGORY, ExceptionType.ENTITY_NOT_FOUND, scopeRequest.getCategoryId().toString());
                });

        try {
            Scope updatedScope = scope
                    .setId(scope.getId())
                    .setName(scopeRequest.getName())
                    .setDescription(scopeRequest.getDescription())
                    .setBackgrounds(scopeRequest.getBackgrounds())
                    .setCategory(category);
            log.debug("ScopeService::updateScope request parameters {}", ValueMapper.jsonAsString(updatedScope));

            Scope savedScope = scopeRepository.save(updatedScope);
            scopeDto = ScopeMapper.toScopeDto(savedScope);
            log.debug("ScopeService::updateScope received response from database {}", ValueMapper.jsonAsString(scopeDto));
        } catch (Exception e) {
            log.error("ScopeService::updateScope execution failed with error {}", e.getMessage());
            throw exception(EntityType.SCOPE, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("ScopeService::updateScope execution completed");
        return scopeDto;
    }

    public ScopeDto deleteScope(Integer id) {
        log.info("ScopeService::deleteScope execution started");
        ScopeDto scopeDto;

        log.debug("ScopeService::deleteScope request parameters id {}", id);
        Scope scope = scopeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("ScopeService::deleteScope execution failed with invalid scope id {}", id);
                    throw exception(EntityType.SCOPE, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        try {
            scopeDto = ScopeMapper.toScopeDto(scope);
            scopeRepository.delete(scope);
            log.debug("ScopeService::deleteScope received response from database {}", ValueMapper.jsonAsString(scopeDto));
        } catch (Exception e) {
            log.error("ScopeService::deleteScope execution failed with error {}", e.getMessage());
            throw exception(EntityType.SCOPE, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("ScopeService::deleteScope execution completed");
        return scopeDto;
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }
}
