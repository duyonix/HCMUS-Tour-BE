package com.onix.hcmustour.dto.mapper;

import com.onix.hcmustour.dto.model.ScopeDto;
import com.onix.hcmustour.model.Scope;

public class ScopeMapper {
    public static ScopeDto toScopeDto(Scope scope) {
        return new ScopeDto()
                .setId(scope.getId())
                .setName(scope.getName())
                .setDescription(scope.getDescription())
                .setBackgrounds(scope.getBackgrounds())
                .setCategoryId(scope.getCategory().getId());
    }
}
