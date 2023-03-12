package com.onix.hcmustour.controller.v1.api;

import com.onix.hcmustour.controller.v1.request.CategoryRequest;
import com.onix.hcmustour.dto.model.CategoryDto;
import com.onix.hcmustour.dto.response.Response;
import com.onix.hcmustour.service.CategoryService;
import com.onix.hcmustour.util.ValueMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response> addCategory(@RequestBody @Valid CategoryRequest categoryRequest) {
        try {
            log.info("CategoryController::addCategory request body {}", ValueMapper.jsonAsString(categoryRequest));
            CategoryDto categoryDto = categoryService.addCategory(categoryRequest);

            Response<Object> response = Response.ok().setPayload(categoryDto);
            log.info("CategoryController::addCategory response {}", ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("CategoryController::addCategory error response {}", ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Response> getCategories() {
        try {
            List<CategoryDto> categories = categoryService.getCategories();
            Response<Object> response = Response.ok().setPayload(categories);
            log.info("CategoryController::getCategories response {}", ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("CategoryController::getCategories error response {}", ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategory(@PathVariable("id") Integer id) {
        try {
            log.info("CategoryController::getCategory by id {}", id);
            CategoryDto category = categoryService.getCategory(id);
            Response<Object> response = Response.ok().setPayload(category);
            log.info("CategoryController::getCategory by id {} response {}", id, ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("CategoryController::getCategory by id {} error response {}", id, ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable("id") Integer id, @RequestBody @Valid CategoryRequest categoryRequest) {
        try {
            log.info("CategoryController::updateCategory by id {} request body {}", id, ValueMapper.jsonAsString(categoryRequest));
            CategoryDto categoryDto = categoryService.updateCategory(id, categoryRequest);

            Response<Object> response = Response.ok().setPayload(categoryDto);
            log.info("CategoryController::updateCategory by id {} response {}", id, ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("CategoryController::updateCategory by id {} error response {}", id, ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable("id") Integer id) {
        try {
            log.info("CategoryController::deleteCategory by id {}", id);
            CategoryDto categoryDto = categoryService.deleteCategory(id);
            Response<Object> response = Response.ok().setPayload(categoryDto);
            log.info("CategoryController::deleteCategory by id {} response {}", id, ValueMapper.jsonAsString(response));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Response<Object> errorResponse = Response.badRequest().setErrors(e.getMessage());
            log.error("CategoryController::deleteCategory by id {} error response {}", id, ValueMapper.jsonAsString(errorResponse));
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
