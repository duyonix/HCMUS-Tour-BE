package com.onix.hcmustour.dto.mapper;

import com.onix.hcmustour.controller.v1.request.ScopeRequest;
import com.onix.hcmustour.dto.model.ScopeDto;
import com.onix.hcmustour.model.Category;
import com.onix.hcmustour.model.Scope;

public class ScopeMapper {
    public static Scope toScope(ScopeRequest scopeRequest, Category category) {
        return new Scope()
                .setName(scopeRequest.getName())
                .setDescription(scopeRequest.getDescription())
                .setLogo(scopeRequest.getLogo())
                .setBackgrounds(scopeRequest.getBackgrounds())
                .setCategory(category)
                .setCoordinate2D(scopeRequest.getCoordinate2D())
                .setCoordinate3D(scopeRequest.getCoordinate3D());
    }

    public static ScopeDto toScopeDto(Scope scope) {
        return new ScopeDto()
                .setId(scope.getId())
                .setName(scope.getName())
                .setDescription(scope.getDescription())
                .setLogo(scope.getLogo())
                .setBackgrounds(scope.getBackgrounds())
                .setCategoryId(scope.getCategory().getId())
                .setCategory(CategoryMapper.toCategoryDto(scope.getCategory()))
                .setCoordinate2D(scope.getCoordinate2D())
                .setCoordinate3D(scope.getCoordinate3D())
                .setCostumes(scope.getCostumes());
    }
}
