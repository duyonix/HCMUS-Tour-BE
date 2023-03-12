package com.onix.hcmustour.service;

import com.onix.hcmustour.controller.v1.request.CategoryRequest;
import com.onix.hcmustour.dto.model.CategoryDto;
import com.onix.hcmustour.exception.ApplicationException;
import com.onix.hcmustour.exception.EntityType;
import com.onix.hcmustour.exception.ExceptionType;
import com.onix.hcmustour.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto addCategory(CategoryRequest categoryRequest) {
        return null;
    }

    public List<CategoryDto> getCategories() {
        return null;
    }

    public CategoryDto getCategory(Integer id) {
        return null;
    }

    public CategoryDto updateCategory(Integer id, CategoryRequest categoryRequest) {
        return null;
    }

    public CategoryDto deleteCategory(Integer id) {
        return null;
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ApplicationException.throwException(entityType, exceptionType, args);
    }

    private RuntimeException exceptionWithId(EntityType entityType, ExceptionType exceptionType, Integer id, String... args) {
        return ApplicationException.throwExceptionWithId(entityType, exceptionType, id, args);
    }
}
