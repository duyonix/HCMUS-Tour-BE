package com.onix.hcmustour.dto.mapper;

import com.onix.hcmustour.controller.v1.request.CostumeRequest;
import com.onix.hcmustour.dto.model.CostumeDto;
import com.onix.hcmustour.model.Costume;
import com.onix.hcmustour.model.Scope;

public class CostumeMapper {
    public static Costume toCostume(CostumeRequest costumeRequest, Scope scope) {
        return new Costume()
                .setName(costumeRequest.getName())
                .setDescription(costumeRequest.getDescription())
                .setPicture(costumeRequest.getPicture())
                .setModel(costumeRequest.getModel())
                .setScope(scope);
    }

    public static CostumeDto toCostumeDto(Costume costume) {
        return new CostumeDto()
                .setId(costume.getId())
                .setName(costume.getName())
                .setDescription(costume.getDescription())
                .setPicture(costume.getPicture())
                .setModel(costume.getModel())
                .setScopeId(costume.getScope().getId())
                .setScope(ScopeMapper.toScopeDtoForCostume(costume.getScope()));
    }
}
