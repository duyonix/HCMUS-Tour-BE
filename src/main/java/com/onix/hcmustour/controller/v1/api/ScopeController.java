package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.controller.v1.request.ScopeRequest;
import com.onix.hcmustour.dto.model.ScopeDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.service.ScopeService;
import com.onix.hcmustour.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scopes")
@Slf4j
public class ScopeController {
    @Autowired
    private ScopeService scopeService;

    @PostMapping
    public ResponseEntity<Response> addScope(@RequestBody @Valid ScopeRequest scopeRequest) {
        log.info("ScopeController::addScope request body {}", ValueMapper.jsonAsString(scopeRequest));
        ScopeDto scopeDto = scopeService.addScope(scopeRequest);

        Response<Object> response = Response.ok().setPayload(scopeDto);
        log.info("ScopeController::addScope response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getScopes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categoryId", required = false) Integer categoryId
    ) {
        log.info("ScopeController::getScopes page {} size {} search {} categoryId {}", page, size, search, categoryId);
        Page<ScopeDto> scopes = scopeService.getScopes(page, size, search, categoryId);
        Response<Object> response = Response.ok().setPayload(scopes);
        log.info("ScopeController::getScopes response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getScope(@PathVariable("id") Integer id) {
        log.info("ScopeController::getScope by id {}", id);
        ScopeDto scope = scopeService.getScope(id);
        Response<Object> response = Response.ok().setPayload(scope);
        log.info("ScopeController::getScope by id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateScope(@PathVariable("id") Integer id, @RequestBody @Valid ScopeRequest scopeRequest) {
        log.info("ScopeController::updateScope by id {} request body {}", id, ValueMapper.jsonAsString(scopeRequest));
        ScopeDto scopeDto = scopeService.updateScope(id, scopeRequest);

        Response<Object> response = Response.ok().setPayload(scopeDto);
        log.info("ScopeController::updateScope by id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteScope(@PathVariable("id") Integer id) {
        log.info("ScopeController::deleteScope by id {}", id);
        ScopeDto scopeDto = scopeService.deleteScope(id);

        Response<Object> response = Response.ok().setPayload(scopeDto);
        log.info("ScopeController::deleteScope by id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
