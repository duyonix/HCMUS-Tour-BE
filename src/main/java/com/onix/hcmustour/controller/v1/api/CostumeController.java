package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.controller.v1.request.CostumeRequest;
import com.onix.hcmustour.dto.model.CostumeDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.service.CostumeService;
import com.onix.hcmustour.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/costumes")
@Slf4j
public class CostumeController {
    @Autowired
    private CostumeService costumeService;

    @PostMapping
    public ResponseEntity<Response> addCostume(@RequestBody @Valid CostumeRequest costumeRequest) {
        log.info("CostumeController::addCostume request body {}", ValueMapper.jsonAsString(costumeRequest));
        CostumeDto costumeDto = costumeService.addCostume(costumeRequest);

        Response<Object> response = Response.ok().setPayload(costumeDto);
        log.info("CostumeController::addCostume response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response> getCostumes(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "scopeId", required = false) Integer scopeId
    ) {
        log.info("CostumeController::getCostumes page {} size {} search {} scopeId {}", page, size, search, scopeId);
        Page<CostumeDto> costumes = costumeService.getCostumes(page, size, search, scopeId);

        Response<Object> response = Response.ok().setPayload(costumes);
        log.info("CostumeController::getCostumes response {}", ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCostume(@PathVariable("id") Integer id) {
        log.info("CostumeController::getCostume by id {}", id);
        CostumeDto costume = costumeService.getCostume(id);

        Response<Object> response = Response.ok().setPayload(costume);
        log.info("CostumeController::getCostume by id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCostume(@PathVariable("id") Integer id, @RequestBody @Valid CostumeRequest costumeRequest) {
        log.info("CostumeController::updateCostume id {} request body {}", id, ValueMapper.jsonAsString(costumeRequest));
        CostumeDto costumeDto = costumeService.updateCostume(id, costumeRequest);

        Response<Object> response = Response.ok().setPayload(costumeDto);
        log.info("CostumeController::updateCostume id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCostume(@PathVariable("id") Integer id) {
        log.info("CostumeController::deleteCostume id {}", id);
        CostumeDto costumeDto = costumeService.deleteCostume(id);

        Response<Object> response = Response.ok().setPayload(costumeDto);
        log.info("CostumeController::deleteCostume id {} response {}", id, ValueMapper.jsonAsString(response));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
