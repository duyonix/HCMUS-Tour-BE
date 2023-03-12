package com.onix.hcmustour.dto.mapper;

import com.onix.hcmustour.controller.v1.request.CategoryRequest;
import com.onix.hcmustour.dto.model.CategoryDto;
import com.onix.hcmustour.model.Category;

public class CategoryMapper {
    public static Category toCategory(CategoryRequest categoryRequest) {
        return new Category()
                .setName(categoryRequest.getName())
                .setDescription(categoryRequest.getDescription())
                .setBackground(categoryRequest.getBackground());
    }

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto()
                .setId(category.getId())
                .setName(category.getName())
                .setDescription(category.getDescription())
                .setBackground(category.getBackground());
    }
}
