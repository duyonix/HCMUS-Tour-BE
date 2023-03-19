package com.onix.hcmustour.service;

import com.onix.hcmustour.controller.v1.request.CostumeRequest;
import com.onix.hcmustour.dto.mapper.CostumeMapper;
import com.onix.hcmustour.dto.model.CostumeDto;
import com.onix.hcmustour.exception.ApplicationException;
import com.onix.hcmustour.exception.EntityType;
import com.onix.hcmustour.exception.ExceptionType;
import com.onix.hcmustour.model.Costume;
import com.onix.hcmustour.model.Scope;
import com.onix.hcmustour.repository.CostumeRepository;
import com.onix.hcmustour.repository.ScopeRepository;
import com.onix.hcmustour.util.ValueMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CostumeService {
    @Autowired
    private CostumeRepository costumeRepository;

    @Autowired
    private ScopeRepository scopeRepository;

    public CostumeDto addCostume(CostumeRequest costumeRequest) {
        log.info("CostumeService::addCostume execution started");
        CostumeDto costumeDto;

        costumeRepository.findByName(costumeRequest.getName())
                .ifPresent(costume -> {
                    log.error("CostumeService::addCostume execution failed with duplicate costume name {}", costumeRequest.getName());
                    throw exception(EntityType.COSTUME, ExceptionType.DUPLICATE_ENTITY, costumeRequest.getName());
                });

        Scope scope = scopeRepository.findById(costumeRequest.getScopeId().longValue())
                .orElseThrow(() -> {
                    log.error("CostumeService::addCostume execution failed with invalid scope id {}", costumeRequest.getScopeId());
                    throw exception(EntityType.SCOPE, ExceptionType.ENTITY_NOT_FOUND, costumeRequest.getScopeId().toString());
                });

        try {
            Costume newCostume = CostumeMapper.toCostume(costumeRequest, scope);
            log.debug("CostumeService::addCostume request parameters {}", ValueMapper.jsonAsString(newCostume));

            Costume savedCostume = costumeRepository.save(newCostume);
            costumeDto = CostumeMapper.toCostumeDto(savedCostume);
            log.debug("CostumeService::addCostume received response from database {}", ValueMapper.jsonAsString(costumeDto));
        } catch (Exception e) {
            log.error("CostumeService::addCostume execution failed with error {}", e.getMessage());
            throw exception(EntityType.COSTUME, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("CostumeService::addCostume execution completed");
        return costumeDto;
    }

    public Page<CostumeDto> getCostumes(Integer page, Integer size, String search, Integer scopeId) {
        log.info("CostumeService::getCostumes execution started");
        Page<CostumeDto> costumeDtos;

        try {
            log.debug("CostumeService::getCostumes request parameters page {}, size {}, search {}, scopeId {}", page, size, search, scopeId);
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            Page<Costume> costumes = costumeRepository.findByNameContainingAndScopeId(search, scopeId, pageable);
            log.info("CostumeService::getCostumes received response from database costumes {}", ValueMapper.jsonAsString(costumes));

            costumeDtos = costumes.map(CostumeMapper::toCostumeDto);
            log.info("CostumeService::getCostumes received response from database {}", ValueMapper.jsonAsString(costumeDtos));
        } catch (Exception e) {
            log.error("CostumeService::getCostumes execution failed with error {}", e.getMessage());
            throw exception(EntityType.COSTUME, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("CostumeService::getCostumes execution completed");
        return costumeDtos;
    }

    public CostumeDto getCostume(Integer id) {
        log.info("CostumeService::getCostume execution started");
        CostumeDto costumeDto;

        log.debug("CostumeService::getCostume request parameters id {}", id);
        Costume costume = costumeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("CostumeService::getCostume execution failed with invalid costume id {}", id);
                    throw exception(EntityType.COSTUME, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        costumeDto = CostumeMapper.toCostumeDto(costume);
        log.debug("CostumeService::getCostume received response from database {}", ValueMapper.jsonAsString(costumeDto));

        log.info("CostumeService::getCostume execution completed");
        return costumeDto;
    }

    public CostumeDto updateCostume(Integer id, CostumeRequest costumeRequest) {
        log.info("CostumeService::updateCostume execution started");
        CostumeDto costumeDto;

        log.debug("CostumeService::updateCostume request parameters id {}, costumeRequest {}", id, ValueMapper.jsonAsString(costumeRequest));
        Costume costume = costumeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("CostumeService::updateCostume execution failed with invalid costume id {}", id);
                    throw exception(EntityType.COSTUME, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        costumeRepository.findByName(costumeRequest.getName())
                .ifPresent(duplicateCostume -> {
                    if (!duplicateCostume.getId().equals(costume.getId())) {
                        log.error("CostumeService::updateCostume execution failed with duplicate costume name {}", costumeRequest.getName());
                        throw exception(EntityType.COSTUME, ExceptionType.DUPLICATE_ENTITY, costumeRequest.getName());
                    }
                });

        Scope scope = scopeRepository.findById(costumeRequest.getScopeId().longValue())
                .orElseThrow(() -> {
                    log.error("CostumeService::updateCostume execution failed with invalid scope id {}", costumeRequest.getScopeId());
                    throw exception(EntityType.SCOPE, ExceptionType.ENTITY_NOT_FOUND, costumeRequest.getScopeId().toString());
                });

        try {
            Costume updatedCostume = CostumeMapper.toCostume(costumeRequest, scope);
            updatedCostume.setId(costume.getId());
            log.debug("CostumeService::updateCostume request parameters {}", ValueMapper.jsonAsString(updatedCostume));

            Costume savedCostume = costumeRepository.save(updatedCostume);
            costumeDto = CostumeMapper.toCostumeDto(savedCostume);
            log.debug("CostumeService::updateCostume received response from database {}", ValueMapper.jsonAsString(costumeDto));
        } catch (Exception e) {
            log.error("CostumeService::updateCostume execution failed with error {}", e.getMessage());
            throw exception(EntityType.COSTUME, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("CostumeService::updateCostume execution completed");
        return costumeDto;
    }

    @Transactional
    public CostumeDto deleteCostume(Integer id) {
        log.info("CostumeService::deleteCostume execution started");
        CostumeDto costumeDto;

        log.debug("CostumeService::deleteCostume request parameters id {}", id);
        Costume costume = costumeRepository.findById(id.longValue())
                .orElseThrow(() -> {
                    log.error("CostumeService::deleteCostume execution failed with invalid costume id {}", id);
                    throw exception(EntityType.COSTUME, ExceptionType.ENTITY_NOT_FOUND, id.toString());
                });

        try {
            costumeDto = CostumeMapper.toCostumeDto(costume);
            costumeRepository.delete(costume);
            log.debug("CostumeService::deleteCostume received response from database {}", ValueMapper.jsonAsString(costumeDto));
        } catch (Exception e) {
            log.error("CostumeService::deleteCostume execution failed with error {}", e.getMessage());
            throw exception(EntityType.COSTUME, ExceptionType.ENTITY_EXCEPTION, e.getMessage());
        }

        log.info("CostumeService::deleteCostume execution completed");
        return costumeDto;
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }
}
